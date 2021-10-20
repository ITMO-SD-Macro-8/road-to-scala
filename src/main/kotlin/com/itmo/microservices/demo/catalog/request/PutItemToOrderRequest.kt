package com.itmo.microservices.demo.catalog.request

import java.util.*

data class PutItemToOrderRequest(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Int
)