package com.itmo.microservices.demo.users.api.service

import com.itmo.microservices.demo.users.api.model.*
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface UserService {
    fun findUser(userId: UUID): AppUserModel?
    fun findUserByUsername(username: String): AppUserModel?
    fun registerUser(request: RegistrationRequest) : UserDto
    fun requestPasswordRestore(request: RestorePasswordRequest)
    fun verifyNewPassword(request: VerifyNewPasswordRequest)
    fun getAccountData(requester: UUID): UserDto
}