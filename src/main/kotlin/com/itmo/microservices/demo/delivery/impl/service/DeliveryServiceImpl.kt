package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.delivery.impl.entity.DeliveryEntity
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus
import com.itmo.microservices.demo.orders.api.model.BookingDto
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.orders.api.service.OrderService
import com.itmo.microservices.demo.orders.impl.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class DeliveryServiceImpl @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val deliveryRepository: DeliveryRepository,
    private val orderService: OrderService
    ) : DeliveryService
{
    override fun getPossibleTimeslots(number: Int): List<Int> {
        return List(number) {Random().nextInt(20) + 1}.distinct().sorted()
    }

    override fun setPreferredTimeSlot(orderId: UUID, slotInSec: Int): BookingDto {
        val order = orderRepository.findById(orderId).orElseThrow{BadRequestException("No order with id = $orderId")}

        if (order.status != OrderStatus.PAID)
            throw BadRequestException("Can SHIP only PAID orders")

        val submissionStartedTime = Instant.now().toEpochMilli()
        val (result, triesCount) = orderService.makeDeliveryRequest()

        val delivery = DeliveryEntity(
            order = order,
            status = if (result.status == TransactionStatus.SUCCESS) DeliverySubmissionOutcome.SUCCESS else DeliverySubmissionOutcome.FAILURE,
            timeslot = slotInSec,
            submittedTime = result.submitTime,
            submissionStartedTime = submissionStartedTime,
            attempts = triesCount,
            transactionId = UUID.fromString(result.id)
        )

        deliveryRepository.save(delivery)

        if (delivery.status == DeliverySubmissionOutcome.SUCCESS){
            orderRepository.save(order.copy(status = OrderStatus.SHIPPING))
        }
        else{
            orderService.resetBooking(order, OrderStatus.REFUND)
        }

        return BookingDto(id = order.id)
    }
}