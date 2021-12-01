package com.itmo.microservices.demo.users.impl.entity

import com.itmo.microservices.demo.users.api.model.UserAppModel
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "users")
data class UserEntity @JvmOverloads constructor(
        @Id val id: UUID = UUID.randomUUID(),
        @Column(unique = true) val username: String = "",
        val password: String = ""
) {
    fun toAppModel() = UserAppModel(id, username, password)
}