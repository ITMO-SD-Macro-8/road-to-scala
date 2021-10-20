package com.itmo.microservices.demo.catalog.model

import com.itmo.microservices.demo.users.impl.entity.AppUser
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class Order(
    val orderId: UUID = UUID.randomUUID(),
    val user: AppUser = AppUser(),
    val date: Date = Date(),
    val positions: List<Position> = emptyList()
)