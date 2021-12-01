package com.itmo.microservices.demo.users.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserAppModel(
        val id: UUID,
        val name: String,
        @JsonIgnore val password: String
) {
        fun userDetails(): UserDetails = User(name, password, emptyList())

        fun toApiModel(): UserApiModel = UserApiModel(id, name)

        fun toEntity() = UserEntity(id, name, password)
}
