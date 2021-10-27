package com.itmo.microservices.demo.users.api.service

import com.itmo.microservices.demo.users.api.model.AppUserModel
import com.itmo.microservices.demo.users.api.model.RegistrationRequest
import com.itmo.microservices.demo.users.api.model.RestorePasswordRequest
import com.itmo.microservices.demo.users.api.model.VerifyNewPasswordRequest
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface UserService {
    fun findUser(userId: UUID): AppUserModel?
    fun findUserByUsername(username: String): AppUserModel?
    fun registerUser(request: RegistrationRequest)
    fun requestPasswordRestore(request: RestorePasswordRequest)
    fun verifyNewPassword(request: VerifyNewPasswordRequest)
    fun getAccountData(requester: UserDetails): AppUserModel
}