package com.itmo.microservices.demo.notifications.impl.service

import com.itmo.microservices.demo.notifications.api.service.NotificationService
import com.itmo.microservices.demo.users.api.model.UserAppModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoggingNotificationService : NotificationService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(LoggingNotificationService::class.java)
    }

    override fun processNewUser(user: UserAppModel) {
        log.info("$user was created & should be notified")
    }
}