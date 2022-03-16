package com.itmo.microservices.demo.orders.impl.service

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.finlog.api.model.FinancialOperationType
import com.itmo.microservices.demo.items.impl.repository.CatalogItemRepository
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.orders.api.model.PaymentStatus
import com.itmo.microservices.demo.orders.api.model.PaymentSubmissionApiModel
import com.itmo.microservices.demo.orders.api.service.PaymentService
import com.itmo.microservices.demo.orders.impl.entity.PaymentEntity
import com.itmo.microservices.demo.orders.impl.repository.OrderRepository
import com.itmo.microservices.demo.orders.impl.repository.PaymentRepository
import com.itmo.microservices.demo.users.api.model.UserAppModel
import com.itmo.microservices.demo.users.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class PaymentServiceImpl(
    private val userService: UserService,
    private val orderRepository: OrderRepository,
    private val catalogItemRepository: CatalogItemRepository,
    private val paymentRepository: PaymentRepository
): PaymentService {

    override fun paymentProceed(principal: Principal, orderId: UUID) : PaymentSubmissionApiModel
    {
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("No order with id = $orderId") }
        val user = extractUserFromPrincipal(principal)
        if (order.status != OrderStatus.BOOKED)
            throw BadRequestException("You can pay only for BOOKED status!")

        orderRepository.save(order.copy(status = OrderStatus.PAID))

        val amount = order.positions.sumOf { item ->
            item.amount * catalogItemRepository.findById(item.catalogItemId).orElseThrow().price
        }

        //TODO: external payment service request
        val prPayment = PaymentEntity(timestamp = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond(),
                                      operationtype = FinancialOperationType.WITHDRAW,
                                      status = PaymentStatus.SUCCESS,
                                      transactionId = UUID.randomUUID(),
                                      order = order,
                                      userId = user.id,
                                      amount = amount)

        paymentRepository.save(prPayment)

        return PaymentSubmissionApiModel(timestamp = prPayment.timestamp, transactionId = prPayment.transactionId)
    }

    private fun extractUserFromPrincipal(authentication: Principal): UserAppModel {
        if (authentication !is Authentication)
            throw BadRequestException("Principal is not authenticated")
        return (authentication.principal as? UserDetails)?.let { userService.findUserByUsername(it.username) }
            ?: throw BadRequestException("Principal is incorrect or there is no such user")
    }
}