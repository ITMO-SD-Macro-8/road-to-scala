package com.itmo.microservices.demo.catalog.api.service

import com.itmo.microservices.demo.catalog.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem

interface CatalogService {
    fun allCatalogItems(): List<CatalogItem>
    fun addCatalogItem(catalogItemRequest: AddCatalogItemRequest): CatalogItem
}