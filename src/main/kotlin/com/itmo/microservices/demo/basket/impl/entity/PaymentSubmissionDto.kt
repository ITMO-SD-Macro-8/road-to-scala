package com.itmo.microservices.demo.basket.impl.entity

import java.util.*

class PaymentSubmissionDto constructor(
    timestamp: Long = 0,
    transactionId: UUID = UUID.randomUUID()
)