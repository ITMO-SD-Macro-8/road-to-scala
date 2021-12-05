package com.itmo.microservices.demo.delivery.impl.service

interface DeliveryService {
    fun getPossibleTimeSlots (number: Int): List<Int>
}