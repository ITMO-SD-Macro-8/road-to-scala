package com.itmo.microservices.demo.items.api.model

import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity

data class AddCatalogItemRequest(
    val title: String,
    val description: String,
    val price: Int,
    val amount: Int
) {
    fun toCatalogItem() = CatalogItemEntity(
        title = title,
        description = description,
        price = price,
        amount = amount
    )
}