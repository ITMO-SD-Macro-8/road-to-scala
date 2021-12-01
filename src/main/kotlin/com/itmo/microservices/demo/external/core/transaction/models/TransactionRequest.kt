package com.itmo.microservices.demo.external.core.transaction.models

import kotlinx.serialization.*

@Serializable
data class TransactionRequest(
    val clientSecret: String
)
