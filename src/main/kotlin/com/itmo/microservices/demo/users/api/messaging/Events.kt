package com.itmo.microservices.demo.users.api.messaging

import com.itmo.microservices.demo.users.api.model.UserAppModel

data class UserCreatedEvent(val user: UserAppModel)
