package com.itmo.microservices.demo.items.impl.service

import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.items.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.items.api.service.CatalogItemService
import com.itmo.microservices.demo.items.impl.entity.CatalogItemEntity
import com.itmo.microservices.demo.items.impl.logging.CatalogItemServiceNotableEvent
import com.itmo.microservices.demo.items.impl.repository.CatalogItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CatalogServiceImpl @Autowired constructor(
    private val catalogRepository: CatalogItemRepository
): CatalogItemService {

    @InjectEventLogger
    lateinit var eventLogger: EventLogger

    override fun allCatalogItems(available: Boolean): List<CatalogItemEntity> {
        return if (available) {
            catalogRepository.findAllByAmountGreaterThan(AVAILABLE_THRESHOLD)
        } else {
            catalogRepository.findAllByAmountEquals(AVAILABLE_THRESHOLD)
        }
    }

    override fun addCatalogItem(catalogItemRequest: AddCatalogItemRequest): CatalogItemEntity {
        return catalogRepository.save(createCatalogItemFromRequest(catalogItemRequest)).also {
            eventLogger.info(CatalogItemServiceNotableEvent, it)
        }
    }

    private fun createCatalogItemFromRequest(request: AddCatalogItemRequest) = CatalogItemEntity(
        title = request.title,
        description = request.description,
        price = request.price,
        amount = request.amount
    )

    private companion object {
        const val AVAILABLE_THRESHOLD = 0
    }
}