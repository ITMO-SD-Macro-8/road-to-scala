package com.itmo.microservices.demo.users.api.model

data class VerifyNewPasswordRequest(
    val verificationCode: String,
    val newPassword: String
)