package com.itmo.microservices.demo.orders.impl.repository

import com.itmo.microservices.demo.orders.impl.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaymentRepository: JpaRepository<PaymentEntity, UUID>{
    fun findAllByOrderId(orderId: UUID): List<PaymentEntity>
}