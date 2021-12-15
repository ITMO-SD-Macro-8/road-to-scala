package com.itmo.microservices.demo.delivery.impl.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset

@Service
class DeliveryServiceImpl : DeliveryService {
    override fun getPossibleTimeslots(number: Int): List<Int> {
        val dateNow = LocalDate.now()

        val tomorrowsTimeSlot = dateNow.plusDays(1).atTime(12, 0).atZone(ZoneOffset.UTC)

        val list = arrayListOf(tomorrowsTimeSlot)
        while (list.size != number){
            list.add(list.last().plusDays(1))
        }

        return list.map { dt -> dt.toEpochSecond().toInt() }
    }
}