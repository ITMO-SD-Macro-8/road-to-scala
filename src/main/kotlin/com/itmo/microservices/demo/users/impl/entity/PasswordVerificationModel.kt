package com.itmo.microservices.demo.users.impl.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PasswordVerificationModel @JvmOverloads constructor(
    @Id val verification: String? = null,
    val userId: String? = null
)