package com.itmo.microservices.demo.users.impl.service

import com.itmo.microservices.demo.users.impl.entity.AppUser
import com.itmo.microservices.demo.users.impl.entity.PasswordVerificationModel
import com.itmo.microservices.demo.users.impl.repository.PasswordRestorationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*


@Component
class EmailService @Autowired constructor(
    private val mailSender: JavaMailSender,
    private val passwordRestorationRepository: PasswordRestorationRepository
) {
    fun sendVerificationCodeToUser(user: AppUser) {
        val verificationCode = UUID.randomUUID().toString()
        val message = SimpleMailMessage().apply {
            setFrom("roadtoscala2@gmail.com")
            setTo(user.email)
            setSubject(user.username)
            setText(verificationCode)
        }
        mailSender.send(message)
        passwordRestorationRepository.save(PasswordVerificationModel(verificationCode, user.username))
    }
}