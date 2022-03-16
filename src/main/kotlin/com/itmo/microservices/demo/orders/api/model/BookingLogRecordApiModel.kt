package com.itmo.microservices.demo.orders.api.model

import java.util.*

data class BookingLogRecordApiModel(
    val bookingId: UUID,
    val itemId: UUID,
    val status: BookingStatus,
    val amount: Int,
    val timestamp: Long
)