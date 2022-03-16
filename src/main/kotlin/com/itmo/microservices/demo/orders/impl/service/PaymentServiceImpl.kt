package com.itmo.microservices.demo.orders.impl.service

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.connector.ConnectorUser
import com.itmo.microservices.demo.external.core.transaction.ExternalServiceRequestException
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import com.itmo.microservices.demo.external.payment.PaymentServiceConnector
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

        val amount = order.positions.sumOf { item ->
            item.amount * catalogItemRepository.findById(item.catalogItemId).orElseThrow().price
        }

        val result = makePaymentRequest()
        val prPayment = PaymentEntity(timestamp = result.submitTime,
                                      operationtype = FinancialOperationType.WITHDRAW,
                                      status = if (result.status == TransactionStatus.SUCCESS) PaymentStatus.SUCCESS else PaymentStatus.FAILED,
                                      transactionId = UUID.fromString(result.id),
                                      order = order,
                                      userId = user.id,
                                      amount = amount)

        paymentRepository.save(prPayment)

        if (prPayment.status == PaymentStatus.SUCCESS){
            orderRepository.save(order.copy(status = OrderStatus.PAID))
        }

        return PaymentSubmissionApiModel(timestamp = prPayment.timestamp, transactionId = prPayment.transactionId)
    }

    private fun extractUserFromPrincipal(authentication: Principal): UserAppModel {
        if (authentication !is Authentication)
            throw BadRequestException("Principal is not authenticated")
        return (authentication.principal as? UserDetails)?.let { userService.findUserByUsername(it.username) }
            ?: throw BadRequestException("Principal is incorrect or there is no such user")
    }

    private fun makePaymentRequest(): TransactionResponse {
        val user = ConnectorUser("225133b4-87c3-45ed-b83c-522c8c8db7c2");
        val settings = ConnectorParameters("http://77.234.215.138:30027/", user);

        val connector = PaymentServiceConnector(settings);

        val transaction = TransactionRequest(user.clientSecret)

        do
        {
            try
            {
                return connector.makeTransaction( "transactions", transaction)
            }
            catch(e: ExternalServiceRequestException)
            {
                print(e.message)
            }
        }
        while (true)
    }
}