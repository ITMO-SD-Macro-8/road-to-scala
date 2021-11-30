package com.itmo.microservices.demo.catalog.impl.entity

import java.util.*

class OrderDto constructor(
    val id: UUID = UUID.randomUUID(),
    val timeCreated: Long = 0,
    val status: OrderStatus = OrderStatus.COMPLETED,
    val itemsMap: Map<UUID, Int> = emptyMap(),
    val deliveryDuration: Int? = null,
    val paymentHistory: List<PaymentLogRecordDto> = emptyList()
)