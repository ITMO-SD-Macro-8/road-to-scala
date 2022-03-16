package com.itmo.microservices.demo.finlog.api.model

import java.util.*

class UserAccountFinancialLogRecordDto constructor(
    val type: FinancialOperationType,
    val amount: Int,
    val orderId: UUID,
    val paymentTransactionId: UUID,
    val timestamp: Long
)