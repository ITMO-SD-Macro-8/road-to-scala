package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.delivery.impl.entity.DeliveryEntity
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.orders.api.model.BookingDto
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.orders.impl.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@Service
class DeliveryServiceImpl @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository
    ) : DeliveryService
{
    override fun getPossibleTimeslots(number: Int): List<Int> {
        val dateNow = LocalDate.now()

        val tomorrowsTimeSlot = dateNow.plusDays(1).atTime(12, 0).atZone(ZoneOffset.UTC)

        val list = arrayListOf(tomorrowsTimeSlot)
        while (list.size != number){
            list.add(list.last().plusDays(1))
        }

        return list.map { dt -> dt.toEpochSecond().toInt() }
    }

    override fun setPreferredTimeSlot(orderId: UUID, slotInSec: Int): BookingDto {
        val order = orderRepository.findById(orderId).orElseThrow{BadRequestException("No order with id = $orderId")}
        val deliveryEntity = DeliveryEntity(order = order)

        deliveryRepository.save(deliveryEntity)
        orderRepository.save(order.copy(status = OrderStatus.SHIPPING))

        return BookingDto(id = deliveryEntity.id)
    }
}