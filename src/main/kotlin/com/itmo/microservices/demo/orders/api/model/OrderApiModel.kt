package com.itmo.microservices.demo.orders.api.model

import java.util.*

class OrderApiModel(
    val id: UUID = UUID.randomUUID(),
    val timeCreated: Long = System.currentTimeMillis(),
    val status: OrderStatus = OrderStatus.COMPLETED,
    val itemsMap: Map<UUID, Int> = emptyMap(),
    var deliveryDuration: Int? = null,
    var paymentHistory: List<PaymentLogRecordDto> = emptyList()
)