package com.itmo.microservices.demo.users.api.messaging

import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.users.api.model.AppUserModel

data class UserCreatedEvent(val user: AppUserModel)

data class OrderCreatedEvent(val order: OrderModel)
