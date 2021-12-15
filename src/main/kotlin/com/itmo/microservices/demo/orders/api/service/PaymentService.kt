package com.itmo.microservices.demo.orders.api.service

import com.itmo.microservices.demo.orders.api.model.PaymentSubmissionApiModel
import java.security.Principal
import java.util.*

interface PaymentService {
    fun paymentProceed(principal: Principal, orderId: UUID) : PaymentSubmissionApiModel
}