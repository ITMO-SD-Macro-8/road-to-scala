package com.itmo.microservices.demo.finlog.api.model

import java.util.*

class UserAccountFinancialLogRecordDto constructor(
    type: FinancialOperationType = FinancialOperationType.WITHDRAW,
    amount: Int = 0,
    orderId: UUID = UUID.randomUUID(),
    paymentTransactionId: UUID = UUID.randomUUID(),
    timestamp: Long = 0
)