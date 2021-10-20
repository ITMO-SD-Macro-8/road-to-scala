package com.itmo.microservices.demo.catalog.model

import java.util.*
import javax.persistence.*

@Entity
data class GoodsItem(
        @Id @GeneratedValue val id: Int = -1,
        val goodItemId: UUID = UUID.randomUUID(),
        @Convert(converter = Price.PriceConverter::class) val price: Price = Price.NONE,
        val name: String = "",
        val description: String = ""
)