package com.itmo.microservices.demo.items.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class CatalogItemApiModel(
    val id: UUID,
    val title: String,
    val description: String,
    val price: Int,
    val amount: Int
) {
    @get:JsonIgnore
    val available: Boolean = amount > 0
}