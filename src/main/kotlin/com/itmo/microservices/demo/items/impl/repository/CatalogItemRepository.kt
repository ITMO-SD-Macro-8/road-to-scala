package com.itmo.microservices.demo.items.impl.repository

import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CatalogItemRepository: JpaRepository<CatalogItemEntity, UUID> {
    fun findAllByAmountGreaterThan(amount: Int): List<CatalogItemEntity>
    fun findAllByAmountEquals(amount: Int): List<CatalogItemEntity>
}