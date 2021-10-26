package com.itmo.microservices.demo.payment.service

import com.itmo.microservices.demo.catalog.model.Order
import com.itmo.microservices.demo.payment.entity.Payment

interface PaymentService
{
    fun makePayment(order: Order): Payment

    fun rollbackPayment(order: Order, payment: Payment)

    fun makeRefund(order: Order, payment: Payment)
}