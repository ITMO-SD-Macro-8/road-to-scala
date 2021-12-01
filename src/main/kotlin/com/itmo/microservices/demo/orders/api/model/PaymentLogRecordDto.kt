package com.itmo.microservices.demo.orders.api.model

import java.util.UUID

class PaymentLogRecordDto constructor(
    val timestamp: Long,
    val status: PaymentStatus,
    val amount: Int,
    val transactionId: UUID,
)