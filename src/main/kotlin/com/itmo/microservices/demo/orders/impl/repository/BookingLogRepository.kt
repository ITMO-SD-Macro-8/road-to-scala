package com.itmo.microservices.demo.orders.impl.repository

import com.itmo.microservices.demo.orders.impl.entity.BookingLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookingLogRepository: JpaRepository<BookingLogEntity, UUID>
{
    fun findAllByBookingId(bookingId: UUID): List<BookingLogEntity>
}