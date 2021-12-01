package com.itmo.microservices.demo.external.core.transaction.models

import com.itmo.microservices.demo.external.core.transaction.TransactionStatus
import kotlinx.serialization.*

@Serializable
data class TransactionResponse (
    val id: String,
    val status: TransactionStatus,
    val submitTime: Long,
    val completedTime: Long?,
    val cost: Int?,
    val delta: Int
)