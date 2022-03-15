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
        //TODO: external delivery service request and log
        return List(number) {Random().nextInt(20) + 1}.distinct().sorted()
    }

    override fun setPreferredTimeSlot(orderId: UUID, slotInSec: Int): BookingDto {
        val order = orderRepository.findById(orderId).orElseThrow{BadRequestException("No order with id = $orderId")}

        if (order.status != OrderStatus.PAID)
            throw BadRequestException("Can SHIP only PAID orders")

        //TODO Delivery log

        val deliveryEntity = DeliveryEntity(order = order)
        deliveryRepository.save(deliveryEntity)
        orderRepository.save(order.copy(status = OrderStatus.SHIPPING))

        return BookingDto(id = deliveryEntity.id)
    }
}