package com.itmo.microservices.demo.catalog.api.model

import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem

data class AddCatalogItemRequest(
    val title: String,
    val description: String,
    val price: Int,
    val amount: Int
) {
    fun toCatalogItem() = CatalogItem(
        title = title,
        description = description,
        price = price,
        amount = amount
    )
}