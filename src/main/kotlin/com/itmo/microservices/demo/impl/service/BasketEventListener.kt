package com.itmo.microservices.demo.basket.impl.service

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe
import com.itmo.microservices.demo.basket.api.service.BasketService
import com.itmo.microservices.demo.users.api.messaging.UserCreatedEvent
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Suppress("UnstableApiUsage")
@Component
class BasketEventListener(private val basketService: BasketService) {

    private val executor = Executors.newFixedThreadPool(2)

    @Subscribe
    @AllowConcurrentEvents
    fun accept(event: UserCreatedEvent) = executor.execute {
        basketService.processNewUser(event.user)
    }
}