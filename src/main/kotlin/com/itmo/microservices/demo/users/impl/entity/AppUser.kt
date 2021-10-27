package com.itmo.microservices.demo.users.impl.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AppUser @JvmOverloads constructor(
        @Id val id: UUID = UUID.randomUUID(),
        @Column(unique = true) val username: String = "",
        val password: String = "",
        val email: String = "",
)