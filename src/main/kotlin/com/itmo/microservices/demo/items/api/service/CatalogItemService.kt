package com.itmo.microservices.demo.items.api.service

import com.itmo.microservices.demo.items.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity

interface CatalogItemService {
    fun allCatalogItems(available: Boolean): List<CatalogItemEntity>
    fun addCatalogItem(catalogItemRequest: AddCatalogItemRequest): CatalogItemEntity
}