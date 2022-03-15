package com.itmo.microservices.demo.finlog.api.model

import java.util.*

class UserAccountFinancialLogRecordDto constructor(
    val type: FinancialOperationType = FinancialOperationType.WITHDRAW,
    val amount: Int = 0,
    val orderId: UUID = UUID.randomUUID(),
    val paymentTransactionId: UUID = UUID.randomUUID(),
    val timestamp: Long = 0
)