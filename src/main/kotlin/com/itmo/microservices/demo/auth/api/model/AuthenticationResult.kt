package com.itmo.microservices.demo.auth.api.model

data class TokenResponseDto(val accessToken: String, val refreshToken: String)
