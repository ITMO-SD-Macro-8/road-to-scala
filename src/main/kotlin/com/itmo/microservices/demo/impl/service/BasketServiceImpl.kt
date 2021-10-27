package com.itmo.microservices.demo.basket.impl.service

import com.itmo.microservices.demo.basket.api.service.BasketService
import com.itmo.microservices.demo.basket.impl.entity.BasketEntity
import com.itmo.microservices.demo.basket.impl.entity.BasketEntry
import com.itmo.microservices.demo.basket.impl.repository.BasketEntityRepository
import com.itmo.microservices.demo.basket.impl.repository.BasketEntryRepository
import com.itmo.microservices.demo.catalog.impl.repository.CatalogRepository
import com.itmo.microservices.demo.users.api.model.AppUserModel
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class BasketServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val basketEntityRepository: BasketEntityRepository,
    private val basketEntryRepository: BasketEntryRepository,
    private val catalogRepository: CatalogRepository
): BasketService {

    override fun processNewUser(user: AppUserModel) {
        val appUser = userRepository.findAppUserByUsername(user.username)
                .orElseThrow { IllegalArgumentException() }
        basketEntityRepository.save(BasketEntity(userId = appUser.id))
    }

    override fun getBasket(userDetails: UserDetails): BasketEntity {
        val appUser = userRepository.findAppUserByUsername(userDetails.username)
                .orElseThrow { IllegalArgumentException() }
        return basketEntityRepository.findBasketEntityByUserId(appUser.id)
                .orElseThrow { IllegalArgumentException() }
    }

    override fun getBasketAndClear(userDetails: UserDetails): BasketEntity {
        val basket = getBasket(userDetails)
        val result = basket.copy()

        basket.entries.forEach {
            basketEntryRepository.delete(it)
        }
        basket.entries.clear()
        basketEntityRepository.save(basket)

        return result
    }

    override fun addCatalogItemToBasket(userDetails: UserDetails, catalogItemId: UUID, amount: Int) {
        val catalogItem = catalogRepository.findById(catalogItemId)
                .orElseThrow { IllegalArgumentException() }
        val basket = getBasket(userDetails).also { basket ->
            val entry = basketEntryRepository.save(
                    BasketEntry(basketEntityId = basket.id, catalogItemId = catalogItem.id, amount = amount)
            )
            basket.entries.add(entry)
        }
        basketEntityRepository.save(basket)
    }
}