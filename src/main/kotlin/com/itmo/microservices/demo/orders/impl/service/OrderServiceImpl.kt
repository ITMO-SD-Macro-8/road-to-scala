package com.itmo.microservices.demo.orders.impl.service

import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.connector.ConnectorUser
import com.itmo.microservices.demo.external.core.transaction.ExternalServiceRequestException
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import com.itmo.microservices.demo.external.delivery.DeliveryServiceConnector
import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity
import com.itmo.microservices.demo.items.impl.repository.CatalogItemRepository
import com.itmo.microservices.demo.orders.api.model.*
import com.itmo.microservices.demo.orders.api.service.OrderService
import com.itmo.microservices.demo.orders.impl.entity.BookingLogEntity
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import com.itmo.microservices.demo.orders.impl.entity.OrderPositionEntity
import com.itmo.microservices.demo.orders.impl.logging.OrderCreatedNotableEvent
import com.itmo.microservices.demo.orders.impl.repository.BookingLogRepository
import com.itmo.microservices.demo.orders.impl.repository.OrderPositionsRepository
import com.itmo.microservices.demo.orders.impl.repository.OrderRepository
import com.itmo.microservices.demo.orders.impl.repository.PaymentRepository
import com.itmo.microservices.demo.users.api.model.UserAppModel
import com.itmo.microservices.demo.users.api.service.UserService
import org.hibernate.criterion.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal
import java.util.*

@Service
class OrderServiceImpl @Autowired constructor(
    private val userService: UserService,
    private val orderRepository: OrderRepository,
    private val orderPositionsRepository: OrderPositionsRepository,
    private val catalogItemRepository: CatalogItemRepository,
    private val paymentRepository: PaymentRepository,
    private val bookingLogRepository: BookingLogRepository,
    private val deliveryRepository: DeliveryRepository
): OrderService {

    @InjectEventLogger
    lateinit var eventLogger: EventLogger

    override fun createNewOrder(principal: Principal): OrderApiModel {
        val user = extractUserFromPrincipal(principal)
        return orderRepository.save(OrderEntity(owner = user.toEntity())).also {
            eventLogger.info(OrderCreatedNotableEvent, it)
        }.toApiModel()
    }

    // Need to add info about delivery duration and payment histories
    override fun getOrderInfo(orderId: UUID): OrderApiModel {
        val order = orderRepository.findById(orderId)
                .orElseThrow { NotFoundException("No order with id = $orderId") }

        val apiModel = order.toApiModel()
        apiModel.paymentHistory = paymentRepository.findAllByOrderId(orderId).map { p -> p.toLogRecord() }
        apiModel.deliveryDuration = deliveryRepository.findAllByOrderId(orderId).firstOrNull{x -> x.status == DeliverySubmissionOutcome.SUCCESS}?.timeslot

        return apiModel
    }

    override fun putCatalogItemToOrder(orderId: UUID, itemId: UUID, amount: Int) {
        var order = orderRepository.findById(orderId).orElseThrow { NotFoundException("No order with id = $orderId") }

        if (order.status != OrderStatus.COLLECTING) {
            if (order.status == OrderStatus.BOOKED) {
                resetBooking(order, OrderStatus.COLLECTING)
                order = orderRepository.findById(orderId).get()
            }
            else {
                throw BadRequestException("You can add items into orders only with COLLECTING or BOOKED status!")
            }
        }

        val catalogItem = catalogItemRepository.findById(itemId)
                .orElseThrow { NotFoundException("No catalog item with id = $itemId") }

        val position = getOrCreateOrderPosition(order, catalogItem).let { it.copy(amount = it.amount + amount) }

        when {
            position.amount > catalogItem.amount ->
                throw BadRequestException("You cannot add more items to the order than there are in the catalog")
            position.amount == 0 -> {
                order.positions.remove(position)
                orderRepository.save(order)
                orderPositionsRepository.delete(position)
            }
            else -> {
                orderPositionsRepository.save(position)
                order.positions.add(position)
                orderRepository.save(order)
            }
        }
    }

    @Transactional
    override fun arrangeBooking(orderId: UUID): BookingDto {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("No order with id = $orderId") }

        when (order.status) {
            OrderStatus.COLLECTING -> {
                val failedItems = HashSet<UUID>()

                order.positions.forEach{ pos ->
                    val item = catalogItemRepository.getById(pos.catalogItemId)
                    var status = BookingStatus.SUCCESS

                    if (item.amount < pos.amount){
                        failedItems.add(item.id)
                        status = BookingStatus.FAILED
                    }
                    else{
                        catalogItemRepository.save(item.copy(amount = item.amount - pos.amount))
                    }

                    bookingLogRepository.save(BookingLogEntity(status = status, bookingId = orderId, itemId = item.id, amount = pos.amount))
                }

                if (failedItems.size == order.positions.size)
                    throw BadRequestException("No items can be booked (don't have enough)")

                orderRepository.save(order.copy(status = OrderStatus.BOOKED, positions = order.positions.filter{ p -> !failedItems.contains(p.catalogItemId)}.toHashSet()))

                return BookingDto(id = order.id, failedIds = failedItems)
            }
            OrderStatus.SHIPPING -> {
                val (result, _) = makeDeliveryRequest()

                if (result.status == TransactionStatus.SUCCESS){
                    orderRepository.save(order.copy(status = OrderStatus.COMPLETED))
                }
                else{
                    resetBooking(order, OrderStatus.REFUND)
                }
            }
            else -> throw BadRequestException("Can only BOOK order with COLLECTING status or FINALIZE it with SHIPPING status")
        }

        return BookingDto(id = order.id)
    }

    override fun getBookingLog(bookingId: UUID): List<BookingLogRecordApiModel>
    {
        return bookingLogRepository.findAllByBookingId(bookingId).map{x -> x.toBookingLogRecordModel()}
    }

    @Transactional
    override fun resetBooking(order: OrderEntity, newStatus: OrderStatus)
    {
        orderRepository.save(order.copy(status = newStatus))

        order.positions.forEach { pos ->
            val item = catalogItemRepository.getById(pos.catalogItemId)
            catalogItemRepository.save(item.copy(amount = item.amount + pos.amount))
        }
    }

    private fun extractUserFromPrincipal(authentication: Principal): UserAppModel {
        if (authentication !is Authentication)
            throw BadRequestException("Principal is not authenticated")
        return (authentication.principal as? UserDetails)?.let { userService.findUserByUsername(it.username) }
                ?: throw BadRequestException("Principal is incorrect or there is no such user")
    }

    private fun getOrCreateOrderPosition(order: OrderEntity, catalogItem: CatalogItemEntity): OrderPositionEntity {
        return order.positions.find { it.catalogItemId == catalogItem.id }
                ?: OrderPositionEntity(catalogItemId = catalogItem.id, order = order, amount = 0)
    }

    override fun makeDeliveryRequest(): Pair<TransactionResponse, Int> {
        val user = ConnectorUser("678391c3-dd29-46b9-b96e-d692699a0c66")
        val settings = ConnectorParameters("http://77.234.215.138:30027/", user)

        val connector = DeliveryServiceConnector(settings)

        val transaction = TransactionRequest(user.clientSecret)

        var attemptsCount = 1
        while (true){
            try
            {
                return Pair(connector.makeTransaction("transactions", transaction), attemptsCount)
            }
            catch (e: ExternalServiceRequestException){
                print(e)
            }

            attemptsCount++;
        }
    }
}