package com.itmo.microservices.demo.delivery.api.models

import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.order.api.model.OrderModel

data class OrderTimeSlot(val order: OrderModel, val timeSlot: TimeSlot) {
}