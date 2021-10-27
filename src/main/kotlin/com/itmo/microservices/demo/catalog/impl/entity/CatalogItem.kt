package com.itmo.microservices.demo.catalog.impl.entity

import java.util.*
import javax.persistence.*

@Entity
data class CatalogItem @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val price: Int = -1,
    val amount: Int = 0
)