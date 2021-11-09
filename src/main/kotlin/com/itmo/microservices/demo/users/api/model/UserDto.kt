package com.itmo.microservices.demo.users.api.model

import java.util.*

data class UserDto(
    val userId : UUID,
    var name: String
)