package com.itmo.microservices.demo.catalog.impl.entity

import java.util.*

class PaymentLogRecordDto constructor(
    timestamp: Long,
    status: PaymentStatus,
    amount: Int,
    transactionId: UUID,
)