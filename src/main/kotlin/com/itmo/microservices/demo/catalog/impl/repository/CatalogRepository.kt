package com.itmo.microservices.demo.catalog.impl.repository

import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CatalogRepository: JpaRepository<CatalogItem, UUID> {
    @Query("SELECT * from catalog_item WHERE catalog_item.amount > 0", nativeQuery = true)
    fun findAllAvailable(): List<CatalogItem>
}