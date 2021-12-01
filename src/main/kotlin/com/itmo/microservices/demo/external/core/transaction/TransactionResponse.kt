package com.itmo.microservices.demo.external.core.transaction

import kotlinx.serialization.*

@Serializable
class TransactionResponse (
    val id: String,
    val status: TransactionStatus,
    val submitTime: Long,
    val completedTime: Long,
    val cost: Int,
    val delta: Int
)