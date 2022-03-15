package com.itmo.microservices.demo.orders.impl.entity

import com.itmo.microservices.demo.finlog.api.model.FinancialOperationType
import com.itmo.microservices.demo.orders.api.model.PaymentStatus
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "payments")
data class PaymentEntity @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    val timestamp: Long,
    val operationtype: FinancialOperationType = FinancialOperationType.WITHDRAW,
    val status: PaymentStatus = PaymentStatus.SUCCESS,
    val amount: Int,
    val transactionId: UUID = UUID.randomUUID(),
    @ManyToOne val order: OrderEntity,
    val userId: UUID
)