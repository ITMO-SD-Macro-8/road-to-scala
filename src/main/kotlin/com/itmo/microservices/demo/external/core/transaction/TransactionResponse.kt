package com.itmo.microservices.demo.external.core.transaction

class TransactionResponse (
    val id: String,
    val status: TransactionStatus,
    val submitTime: Int,
    val completedTime: Int,
    val cost: Int,
    val delta: Int
)