package com.itmo.microservices.demo.delivery.api.dto

import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.order.api.model.OrderModel

data class OrderTimeSlotDto(val order: OrderModel, val timeSlot: TimeSlot)