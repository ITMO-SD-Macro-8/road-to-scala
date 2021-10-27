package com.itmo.microservices.demo.order.api.model

import com.itmo.microservices.demo.basket.impl.entity.BasketEntity
import com.itmo.microservices.demo.basket.impl.entity.BasketEntry
import java.util.*

data class OrderModel(
    val userId: UUID = UUID.randomUUID(),
    val basketEntries: Set<BasketEntry> = emptySet(),
    val timestamp: Long = System.currentTimeMillis()
) {
    constructor(basketEntity: BasketEntity): this(
        basketEntity.userId,
        basketEntity.entries
    )
}