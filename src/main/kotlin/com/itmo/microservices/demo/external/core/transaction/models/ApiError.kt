package com.itmo.microservices.demo.external.core.transaction.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiError (
   val timestamp: Long,
   val message: String
)