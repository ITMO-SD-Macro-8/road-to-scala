package com.itmo.microservices.demo.catalog.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Order(
    @Id @GeneratedValue val id: Int = -1,
    val userId: Int = -1,
    val date: Date = Date(),
    val positions: List<Position> = emptyList()
)