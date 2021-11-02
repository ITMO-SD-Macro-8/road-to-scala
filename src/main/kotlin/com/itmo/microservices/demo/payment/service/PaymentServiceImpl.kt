package com.itmo.microservices.demo.payment.service

import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.payment.entity.Payment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl @Autowired constructor()
    : PaymentService {

    override fun makePayment(order: OrderModel): Payment {
        TODO("Not yet implemented")
    }

    override fun rollbackPayment(order: OrderModel, payment: Payment) {
        TODO("Not yet implemented")
    }

    override fun makeRefund(order: OrderModel, payment: Payment) {
        TODO("Not yet implemented")
    }

}