package com.itmo.microservices.demo.users.api.service

import com.itmo.microservices.demo.users.api.model.*
import java.util.*

interface UserService {
    fun findUser(userId: UUID): UserAppModel?
    fun findUserByUsername(username: String): UserAppModel?
    fun registerUser(request: RegistrationRequest): UserAppModel
    fun getAccountData(userId: UUID): UserAppModel
}