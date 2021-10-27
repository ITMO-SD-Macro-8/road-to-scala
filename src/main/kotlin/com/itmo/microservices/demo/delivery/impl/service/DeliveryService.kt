package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.catalog.model.Order
import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import java.util.*

interface DeliveryService
{
    fun getNearestTimeSlot (preferredTimeSlot: Date): TimeSlot?

    fun reserveTimeSlot(order: Order, timeSlot: TimeSlot): Boolean
}