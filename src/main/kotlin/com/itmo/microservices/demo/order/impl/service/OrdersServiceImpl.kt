package com.itmo.microservices.demo.order.impl.service

import com.google.common.eventbus.EventBus
import com.itmo.microservices.demo.basket.api.service.BasketService
import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.order.api.service.OrderFinalizationService
import com.itmo.microservices.demo.users.api.messaging.OrderCreatedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Suppress("UnstableApiUsage")
@Service
class OrdersServiceImpl @Autowired constructor(
    private val basketService: BasketService,
    private val eventBus: EventBus
): OrderFinalizationService {
    override fun finalizeOrder(userDetails: UserDetails) {
        val basket = basketService.getBasketAndClear(userDetails)
        val order = OrderModel(basket)
        eventBus.post(OrderCreatedEvent(order))
    }
}