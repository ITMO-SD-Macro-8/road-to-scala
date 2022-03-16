package com.itmo.microservices.demo.delivery.impl.repository

import com.itmo.microservices.demo.delivery.impl.entity.DeliveryEntity
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryRepository: JpaRepository<DeliveryEntity, UUID>{
    fun findByOrderId(orderId: UUID): DeliveryEntity?
}