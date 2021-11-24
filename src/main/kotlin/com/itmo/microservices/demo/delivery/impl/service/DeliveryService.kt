package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.order.api.model.OrderModel
import java.time.LocalDate
import java.util.*

interface DeliveryService {
    fun getPossibleTimeSlots (number: Int): List<Int>

    fun reserveTimeSlot(order: OrderModel, timeSlot: TimeSlot): Boolean
}