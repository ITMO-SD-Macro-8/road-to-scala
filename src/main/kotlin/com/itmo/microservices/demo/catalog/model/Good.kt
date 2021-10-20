package com.itmo.microservices.demo.catalog.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Good(
    @Id @GeneratedValue val id: Int = -1,
    val price: Price = Price.NONE,
    val name: String = "",
    val description: String = ""
)