package com.itmo.microservices.demo.orders.api.service

import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.model.BookingDto
import java.security.Principal
import java.util.*

interface OrderService {
    fun createNewOrder(principal: Principal): OrderApiModel
    fun getOrderInfo(orderId: UUID): OrderApiModel
    fun putCatalogItemToOrder(orderId: UUID, itemId: UUID, amount: Int)
    fun arrangeBooking(orderId: UUID): BookingDto
}