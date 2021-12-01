package com.itmo.microservices.demo.external.core.transaction

import kotlinx.serialization.*

@Serializable
data class TransactionRequest(
    val clientSecret: String
)
