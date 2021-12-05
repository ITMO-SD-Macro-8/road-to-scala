package com.itmo.microservices.demo.items.impl.entity

import com.itmo.microservices.demo.items.api.model.CatalogItemApiModel
import java.util.*
import javax.persistence.*

@Entity(name = "catalog_items")
data class CatalogItemEntity @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val price: Int = -1,
    val amount: Int = 0
) {
    fun toApiModel() = CatalogItemApiModel(id, title, description, price, amount)
}