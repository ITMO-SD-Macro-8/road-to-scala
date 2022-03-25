package com.itmo.microservices.demo.users.impl.service

import com.google.common.eventbus.EventBus
import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.common.exception.ConflictException
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.users.api.messaging.UserCreatedEvent
import com.itmo.microservices.demo.users.api.model.*
import com.itmo.microservices.demo.users.api.service.UserService
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import com.itmo.microservices.demo.users.impl.logging.UserServiceNotableEvents
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Suppress("UnstableApiUsage")
@Service
class DefaultUserService(private val userRepository: UserRepository,
                         private val passwordEncoder: PasswordEncoder,
                         private val eventBus: EventBus,
                         ): UserService {

    @InjectEventLogger
    private lateinit var eventLogger: EventLogger

    override fun findUser(userId: UUID): UserAppModel? = userRepository
            .findByIdOrNull(userId)?.toAppModel()

    override fun findUserByUsername(username: String): UserAppModel? = userRepository
            .findAppUserByUsername(username)
            .orElse(null)?.toAppModel()

    override fun registerUser(request: RegistrationRequest) : UserAppModel {
        if (isUsernameTaken(request.username))
            throw ConflictException("Username with same name: ${request.username} already exists")
        val user = createUserFromRequest(request).let { userRepository.save(it).toAppModel() }
        notifyUserCreated(user)
        return user
    }

    override fun getAccountData(userId: UUID): UserAppModel = userRepository
            .findById(userId)
            .orElseThrow {
                NotFoundException("User with id: [$userId] not found")
            }.toAppModel()

    private fun isUsernameTaken(username: String): Boolean {
        val exists = userRepository.findAppUserByUsername(username)
        return exists.isPresent
    }

    private fun createUserFromRequest(request: RegistrationRequest): UserEntity = UserEntity(
        username = request.username,
        password = passwordEncoder.encode(request.password)
    )

    private fun notifyUserCreated(user: UserAppModel) {
        eventBus.post(UserCreatedEvent(user))
        eventLogger.info(UserServiceNotableEvents.I_USER_CREATED, user.name)
    }

}
