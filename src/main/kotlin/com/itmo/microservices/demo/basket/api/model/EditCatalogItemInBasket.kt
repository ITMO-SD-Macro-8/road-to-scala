package com.itmo.microservices.demo.basket.api.model

import java.util.*

data class EditCatalogItemInBasket(
    val catalogItemId: UUID,
    val amount: Int
)