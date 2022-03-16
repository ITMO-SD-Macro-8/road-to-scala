package com.itmo.microservices.demo.orders.api.service

import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.model.BookingDto
import com.itmo.microservices.demo.orders.api.model.BookingLogRecordApiModel
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import java.security.Principal
import java.util.*

interface OrderService {
    fun createNewOrder(principal: Principal): OrderApiModel
    fun getOrderInfo(orderId: UUID): OrderApiModel
    fun putCatalogItemToOrder(orderId: UUID, itemId: UUID, amount: Int)
    fun arrangeBooking(orderId: UUID): BookingDto
    fun getBookingLog(bookingId: UUID): List<BookingLogRecordApiModel>
    fun resetBooking(order: OrderEntity, newStatus: OrderStatus)
    fun makeDeliveryRequest(): Pair<TransactionResponse, Int>
}