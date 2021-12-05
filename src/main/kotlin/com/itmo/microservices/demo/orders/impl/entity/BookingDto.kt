package com.itmo.microservices.demo.orders.impl.entity

import java.util.*

data class BookingDto(
    val id: UUID = UUID.randomUUID(),
    val failedIds: Set<UUID> = emptySet()
)