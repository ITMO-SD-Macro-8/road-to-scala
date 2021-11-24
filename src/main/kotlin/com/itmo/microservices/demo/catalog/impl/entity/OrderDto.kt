package com.itmo.microservices.demo.catalog.impl.entity

import java.util.*

class OrderDto constructor(
    id: UUID = UUID.randomUUID(),
    timeCreated: Long = 0,
    status: OrderStatus = OrderStatus.COMPLETED,
    itemsMap: Map<UUID, Int> = emptyMap(),
    deliveryDuration: Int? = null,
    paymentHistory: List<PaymentLogRecordDto> = emptyList()
)