package com.itmo.microservices.demo.users.api.model

data class RestorePasswordRequest @JvmOverloads constructor(
    val username: String? = null,
    val email: String? = null
)