package com.itmo.microservices.demo.users.api.model

import java.util.*

data class UserDto(
    val id : UUID,
    var name: String
)