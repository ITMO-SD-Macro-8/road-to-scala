package com.itmo.microservices.demo.finlog.impl.service

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.finlog.api.model.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.finlog.api.service.FinlogService
import com.itmo.microservices.demo.orders.impl.repository.PaymentRepository
import com.itmo.microservices.demo.users.api.model.UserAppModel
import com.itmo.microservices.demo.users.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.*

@Service
class FinlogServiceImpl @Autowired constructor(
    private val userService: UserService,
    private val paymentRepository: PaymentRepository
) : FinlogService
{
    override fun operations(principal: Principal): List<UserAccountFinancialLogRecordDto> {
        val user = extractUserFromPrincipal(principal)
        return paymentRepository.findAll()
                                .filter {it.userId == user.id}
                                .map {
                                    UserAccountFinancialLogRecordDto(type = it.operationtype,
                                                                       amount = it.amount,
                                                                       orderId = it.order.id,
                                                                       paymentTransactionId = it.transactionId,
                                                                       timestamp = it.timestamp)}
    }

    override fun operationsWithOrder(principal: Principal, orderId: UUID): List<UserAccountFinancialLogRecordDto> {
        val user = extractUserFromPrincipal(principal)
        return paymentRepository.findAll()
            .filter {it.userId == user.id && it.order.id == orderId}
            .map { it ->
                UserAccountFinancialLogRecordDto(type = it.operationtype,
                    amount = it.amount,
                    orderId = it.order.id,
                    paymentTransactionId = it.transactionId,
                    timestamp = it.timestamp)}
    }

    private fun extractUserFromPrincipal(authentication: Principal): UserAppModel {
        if (authentication !is Authentication)
            throw BadRequestException("Principal is not authenticated")
        return (authentication.principal as? UserDetails)?.let { userService.findUserByUsername(it.username) }
            ?: throw BadRequestException("Principal is incorrect or there is no such user")
    }
}