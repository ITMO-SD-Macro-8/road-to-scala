package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.order.api.model.OrderModel
import java.util.*

interface DeliveryService {
    fun getNearestTimeSlot (preferredTimeSlot: Date): TimeSlot?

    fun reserveTimeSlot(order: OrderModel, timeSlot: TimeSlot): Boolean
}