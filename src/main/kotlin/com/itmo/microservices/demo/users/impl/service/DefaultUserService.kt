package com.itmo.microservices.demo.users.impl.service

import com.google.common.eventbus.EventBus
import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.users.api.messaging.UserCreatedEvent
import com.itmo.microservices.demo.users.api.model.*
import com.itmo.microservices.demo.users.api.service.UserService
import com.itmo.microservices.demo.users.impl.entity.AppUser
import com.itmo.microservices.demo.users.impl.logging.UserServiceNotableEvents
import com.itmo.microservices.demo.users.impl.repository.PasswordRestorationRepository
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import com.itmo.microservices.demo.users.impl.util.toModel
import com.sun.mail.util.UUDecoderStream
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Suppress("UnstableApiUsage")
@Service
class DefaultUserService(private val userRepository: UserRepository,
                         private val passwordEncoder: PasswordEncoder,
                         private val emailService: EmailService,
                         private val passwordRestorationRepository: PasswordRestorationRepository,
                         private val eventBus: EventBus,
                         ): UserService {

    @InjectEventLogger
    private lateinit var eventLogger: EventLogger

    override fun findUser(userId: UUID): AppUserModel? = userRepository
            .findByIdOrNull(userId)?.toModel()

    override fun findUserByUsername(username: String): AppUserModel? = userRepository
            .findAppUserByUsername(username)
            .orElse(null)?.toModel()

    override fun registerUser(request: RegistrationRequest) : UserDto {
        val userEntity = userRepository.save(request.toEntity())
        eventBus.post(UserCreatedEvent(userEntity.toModel()))
        eventLogger.info(UserServiceNotableEvents.I_USER_CREATED, userEntity.username)
        return UserDto(userEntity.id, userEntity.username)
    }

    override fun requestPasswordRestore(request: RestorePasswordRequest) {
        val appUser = when {
            request.email != null -> userRepository.findAppUserByEmail(request.email)
            request.username != null -> userRepository.findAppUserByUsername(request.username)
            else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "One of the parameters should be specified: username or email")
        }.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user") }
        emailService.sendVerificationCodeToUser(appUser)
    }

    override fun verifyNewPassword(request: VerifyNewPasswordRequest) {
        val verificationCode = UUID.fromString(request.verificationCode)
        val restorationModel = passwordRestorationRepository.findById(verificationCode)
                .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "No such verification code") }
        val appUser = userRepository.findAppUserByUsername(restorationModel.userId!!)
                .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user") }
        val encodedPassword = passwordEncoder.encode(request.newPassword)
        if (appUser.password == encodedPassword)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "New password can't be equal to old password")
        userRepository.save(appUser.copy(password = encodedPassword))
        passwordRestorationRepository.delete(restorationModel)
    }

    override fun getAccountData(requester: UUID): UserDto {
        val user = userRepository.findById(requester).orElseThrow {
            throw NotFoundException("User $requester not found")
        }

        return UserDto(user.id, user.username)
    }

    fun RegistrationRequest.toEntity(): AppUser =
        AppUser(username = this.username,
            password = passwordEncoder.encode(this.password)
        )
}
