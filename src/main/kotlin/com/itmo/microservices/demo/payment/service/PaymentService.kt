package com.itmo.microservices.demo.payment.service

import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.payment.entity.Payment

interface PaymentService {
    fun makePayment(order: OrderModel): Payment

    fun rollbackPayment(order: OrderModel, payment: Payment)

    fun makeRefund(order: OrderModel, payment: Payment)
}