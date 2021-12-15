package com.itmo.microservices.demo.orders.impl.entity

import com.itmo.microservices.demo.finlog.api.model.FinancialOperationType
import com.itmo.microservices.demo.orders.api.model.PaymentStatus
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import java.util.*
import javax.persistence.Id
import javax.persistence.ManyToOne

class PaymentEntity (
    @Id val id: UUID = UUID.randomUUID(),
    val timestamp: Long,
    val operationtype: FinancialOperationType,
    val status: PaymentStatus,
    val amount: Int,
    val transactionId: UUID,
    @ManyToOne val order: OrderEntity,
    val userId: UUID
)