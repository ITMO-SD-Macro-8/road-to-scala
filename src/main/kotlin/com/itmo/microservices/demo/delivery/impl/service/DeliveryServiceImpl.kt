package com.itmo.microservices.demo.delivery.impl.service

import org.springframework.stereotype.Service

@Service
class DeliveryServiceImpl : DeliveryService {
    override fun getPossibleTimeSlots(number: Int): List<Int> {
        return listOf(3, 2, 1)
    }
}