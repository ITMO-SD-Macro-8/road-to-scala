package com.itmo.microservices.demo.catalog.impl.service

import com.itmo.microservices.demo.catalog.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.catalog.api.service.CatalogService
import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem
import com.itmo.microservices.demo.catalog.impl.repository.CatalogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CatalogServiceImpl @Autowired constructor(
    private val catalogRepository: CatalogRepository
): CatalogService {

    override fun allCatalogItems(available: Boolean): List<CatalogItem> = if (available)
        catalogRepository.findAllAvailable()
    else
        catalogRepository.findAll()

    override fun addCatalogItem(catalogItemRequest: AddCatalogItemRequest)
        = catalogRepository.save(catalogItemRequest.toCatalogItem())
}