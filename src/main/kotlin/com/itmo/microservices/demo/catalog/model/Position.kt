package com.itmo.microservices.demo.catalog.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Position(
    @Id @GeneratedValue val id: Int = -1,
    val goodId: Int = -1,
    val count: Int = 0
)