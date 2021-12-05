package com.itmo.microservices.demo.users.api.model

import java.util.*

data class UserApiModel(
    val id : UUID,
    var name: String
)