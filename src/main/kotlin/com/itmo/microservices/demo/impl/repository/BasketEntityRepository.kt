package com.itmo.microservices.demo.basket.impl.repository

import com.itmo.microservices.demo.basket.impl.entity.BasketEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BasketEntityRepository: JpaRepository<BasketEntity, UUID> {
    fun findBasketEntityByUserId(userId: UUID): Optional<BasketEntity>
}