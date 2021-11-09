package com.itmo.microservices.demo.auth.api.service

import com.itmo.microservices.demo.auth.api.model.AuthenticationRequest
import com.itmo.microservices.demo.auth.api.model.TokenResponseDto
import org.springframework.security.core.Authentication

interface AuthService {
    fun authenticate(request: AuthenticationRequest): TokenResponseDto
    fun refresh(authentication: Authentication): TokenResponseDto
}