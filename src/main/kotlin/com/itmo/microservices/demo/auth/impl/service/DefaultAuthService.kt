package com.itmo.microservices.demo.auth.impl.service

import com.itmo.microservices.demo.auth.api.model.AuthenticationRequest
import com.itmo.microservices.demo.auth.api.model.TokenResponseDto
import com.itmo.microservices.demo.auth.api.service.AuthService
import com.itmo.microservices.demo.common.exception.AccessDeniedException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.users.api.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DefaultAuthService(private val userService: UserService,
                         private val tokenManager: JwtTokenManager,
                         private val passwordEncoder: PasswordEncoder) : AuthService {

    override fun authenticate(request: AuthenticationRequest): TokenResponseDto {
        val user = userService.findUserByUsername(request.name)
                ?: throw NotFoundException("User with username ${request.name} not found")

        if (!passwordEncoder.matches(request.password, user.password))
            throw AccessDeniedException("Invalid password")

        val accessToken = tokenManager.generateToken(user.userDetails())
        val refreshToken = tokenManager.generateRefreshToken(user.userDetails())
        return TokenResponseDto(accessToken, refreshToken)
    }

    override fun refresh(authentication: Authentication): TokenResponseDto {
        val refreshToken = authentication.credentials as String
        val principal = authentication.principal as UserDetails
        val accessToken = tokenManager.generateToken(principal)
        return TokenResponseDto(accessToken, refreshToken)
    }
}
