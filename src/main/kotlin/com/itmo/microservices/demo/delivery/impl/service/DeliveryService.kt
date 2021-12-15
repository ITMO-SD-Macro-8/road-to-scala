package com.itmo.microservices.demo.delivery.impl.service

interface DeliveryService {
    fun getPossibleTimeslots (number: Int): List<Int>
}