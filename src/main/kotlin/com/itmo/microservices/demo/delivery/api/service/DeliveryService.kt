package com.itmo.microservices.demo.delivery.api.service

import com.itmo.microservices.demo.orders.api.model.BookingDto
import java.util.*

interface DeliveryService {
    fun getPossibleTimeslots (number: Int): List<Int>
    fun setPreferredTimeSlot(orderId: UUID, slotInSec: Int): BookingDto
}