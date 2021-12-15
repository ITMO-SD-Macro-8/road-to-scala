package com.itmo.microservices.demo.orders.api.model

import com.itmo.microservices.demo.orders.impl.entity.BookingStatus
import java.util.*

data class BookingLogRecordApiModel(
    val bookingId: UUID = UUID.randomUUID(),
    val itemId: UUID = UUID.randomUUID(),
    val status: BookingStatus = BookingStatus.SUCCESS,
    val amount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)