package com.itmo.microservices.demo.orders.impl.service

import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity
import com.itmo.microservices.demo.items.impl.repository.CatalogItemRepository
import com.itmo.microservices.demo.orders.api.service.OrderService
import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.orders.api.model.BookingDto
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import com.itmo.microservices.demo.orders.impl.entity.OrderPositionEntity
import com.itmo.microservices.demo.orders.impl.logging.OrderCreatedNotableEvent
import com.itmo.microservices.demo.orders.impl.repository.OrderPositionsRepository
import com.itmo.microservices.demo.orders.impl.repository.OrderRepository
import com.itmo.microservices.demo.orders.impl.repository.PaymentRepository
import com.itmo.microservices.demo.users.api.model.UserAppModel
import com.itmo.microservices.demo.users.api.service.UserService
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
    private val paymentRepository: PaymentRepository
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
        val apiModel = orderRepository.findById(orderId)
                .orElseThrow { NotFoundException("No order with id = $orderId") }
                .toApiModel()

        apiModel.paymentHistory = paymentRepository.findAllByOrderId(orderId).map { p -> p.toLogRecord() }

        return apiModel
    }

    override fun putCatalogItemToOrder(orderId: UUID, itemId: UUID, amount: Int) {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("No order with id = $orderId") }

        if (order.status != OrderStatus.COLLECTING)
            throw BadRequestException("You can add items into orders only with COLLECTING status!")

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
                orderRepository.save(order.copy(status = OrderStatus.BOOKED))

                order.positions.forEach{ pos ->
                    val item = catalogItemRepository.getById(pos.catalogItemId)
                    catalogItemRepository.save(item.copy(amount = item.amount - pos.amount))
                    //TODO BookingHistory Log
                }
            }
            OrderStatus.SHIPPING -> {
                orderRepository.save(order.copy(status = OrderStatus.COMPLETED))
                //TODO external delivery service request (if OK => Completed, else => Refund)
                //TODO Delivery Log
            }
            else -> throw BadRequestException("Can only book order with COLLECTING status or finalize it with SHIPPING status")
        }

        return BookingDto(id = order.id)
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
}