package com.itmo.microservices.demo.payment.models

import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.payment.entity.Payment

data class OrderPayment(val order: OrderModel,
                        val payment: Payment)
{

}