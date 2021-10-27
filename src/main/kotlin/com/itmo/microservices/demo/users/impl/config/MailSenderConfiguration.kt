package com.itmo.microservices.demo.users.impl.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfiguration {
    @Bean
    fun createJavaMailSender(): JavaMailSender = JavaMailSenderImpl().apply {
        host = "smtp.gmail.com"
        port = 587
        username = "roadtoscala2@gmail.com"
        password = "roadtoscala"
        javaMailProperties.apply {
            put("mail.transport.protocol", "smtp");
            put("mail.smtp.auth", "true");
            put("mail.smtp.starttls.enable", "true");
            put("mail.debug", "true");
        }
    }
}