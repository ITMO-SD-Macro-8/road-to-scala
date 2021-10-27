package com.itmo.microservices.demo.basket.api.service

import com.itmo.microservices.demo.basket.impl.entity.BasketEntity
import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem
import com.itmo.microservices.demo.users.api.model.AppUserModel
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface BasketService {
    fun processNewUser(user: AppUserModel)
    fun getBasket(userDetails: UserDetails): BasketEntity
    fun addCatalogItemToBasket(userDetails: UserDetails, catalogItemId: UUID, amount: Int)
    fun getBasketAndClear(userDetails: UserDetails): BasketEntity
}