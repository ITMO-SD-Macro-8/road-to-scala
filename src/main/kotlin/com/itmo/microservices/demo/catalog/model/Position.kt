package com.itmo.microservices.demo.catalog.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Position(
    @Id @GeneratedValue val id: Int = -1,
    val goodId: UUID = UUID.randomUUID(),
    val count: Int = 0
)