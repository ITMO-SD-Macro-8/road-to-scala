package com.itmo.microservices.demo.external.core.transaction.models

import kotlinx.serialization.Serializable

@Serializable
class ApiError (
   val timestamp: Long,
   val message: String
)