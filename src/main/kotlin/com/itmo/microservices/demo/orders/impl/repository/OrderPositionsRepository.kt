package com.itmo.microservices.demo.orders.impl.repository

import com.itmo.microservices.demo.orders.impl.entity.OrderPositionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderPositionsRepository: JpaRepository<OrderPositionEntity, UUID>