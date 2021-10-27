package com.itmo.microservices.demo.users.impl.util

import com.itmo.microservices.demo.users.api.model.AppUserModel
import com.itmo.microservices.demo.users.impl.entity.AppUser

fun AppUser.toModel() = AppUserModel(
    username = this.username,
    email = this.email,
    password = this.password
)