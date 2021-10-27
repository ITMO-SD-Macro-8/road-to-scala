package com.itmo.microservices.demo.basket.impl.repository

import com.itmo.microservices.demo.basket.impl.entity.BasketEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketEntryRepository: JpaRepository<BasketEntry, Int>