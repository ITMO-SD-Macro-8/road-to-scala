package com.itmo.microservices.demo.orders.impl.logging

import com.itmo.microservices.commonlib.logging.NotableEvent

sealed class OrderServiceNotableEvent(private val name: String): NotableEvent {
    override fun getName(): String = name
}

object OrderCreatedNotableEvent: OrderServiceNotableEvent(OrderCreatedNotableEvent::class.java.simpleName) {
    override fun getTemplate(): String = "Created new order = {}"
}