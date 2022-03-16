package com.itmo.microservices.demo.orders.impl.entity

import com.itmo.microservices.demo.orders.api.model.BookingLogRecordApiModel
import com.itmo.microservices.demo.orders.api.model.BookingStatus
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class BookingLogEntity(
    @Id val id: UUID = UUID.randomUUID(),
    @Column(name = "booking_time") val bookingTime: Instant = Instant.now(),
    @Enumerated(EnumType.ORDINAL) val status: BookingStatus,
    val bookingId: UUID,
    val itemId: UUID,
    val amount: Int
)
{
    fun toBookingLogRecordModel(): BookingLogRecordApiModel{
        return BookingLogRecordApiModel(
            bookingId,
            itemId,
            status,
            amount,
            bookingTime.epochSecond
        )
    }
}