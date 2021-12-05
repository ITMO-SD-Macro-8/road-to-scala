package com.itmo.microservices.demo.orders.api.model

import java.util.*

data class PaymentSubmissionApiModel(
    val timestamp: Long = 0,
    val transactionId: UUID = UUID.randomUUID()
)