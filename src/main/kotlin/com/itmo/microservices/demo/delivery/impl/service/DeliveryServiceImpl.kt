package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.order.api.model.OrderModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class DeliveryServiceImpl @Autowired constructor()
    :DeliveryService{

    override fun getNearestTimeSlot(preferredTimeSlot: LocalDate): TimeSlot? {
        return TimeSlot(UUID.randomUUID(), preferredTimeSlot, preferredTimeSlot, 0, 1);
    }

    override fun reserveTimeSlot(order: OrderModel, timeSlot: TimeSlot): Boolean {
        if (timeSlot.reservedCount != timeSlot.reservedMax){
            timeSlot.reservedCount = timeSlot.reservedCount!! + 1

            return true
        }

        return false
    }

}