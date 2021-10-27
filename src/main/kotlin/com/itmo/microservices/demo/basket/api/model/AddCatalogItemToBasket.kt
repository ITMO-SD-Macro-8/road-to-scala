package com.itmo.microservices.demo.basket.api.model

import java.util.*

data class AddCatalogItemToBasket(
    val catalogItemId: UUID,
    val amount: Int
)