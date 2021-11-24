package com.itmo.microservices.demo.basket.api.controller

import com.itmo.microservices.demo.basket.api.model.AddCatalogItemToBasket
import com.itmo.microservices.demo.basket.api.service.BasketService
import com.itmo.microservices.demo.basket.impl.entity.BookingDto
import com.itmo.microservices.demo.basket.impl.repository.BasketEntityRepository
import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class BasketController(
        private val basketService: BasketService,
        private val basketEntityRepository: BasketEntityRepository,
        private val userRepository: UserRepository
) {
    @GetMapping
    @Operation(
        summary = "Get catalog item list from basket",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun getBasket(@Parameter(hidden = true) @AuthenticationPrincipal userDetails: UserDetails)
        = basketService.getBasket(userDetails)

    @PostMapping
    @Operation(
        summary = "Add catalog item to basket",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun addCatalogItem(
        @RequestBody request: AddCatalogItemToBasket,
        @Parameter(hidden = true) @AuthenticationPrincipal userDetails: UserDetails
    ) = basketService.addCatalogItemToBasket(userDetails, request.catalogItemId, request.amount)


    @PutMapping("/{order_id}/items/{item_id}")
    @Operation(
            summary = "Add catalog item to basket",
            responses = [
                ApiResponse(description = "OK", responseCode = "200"),
                ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
            ],
            security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun putItemToOrder(
        @PathVariable(name = "order_id") orderId: UUID,
        @PathVariable(name = "item_id") itemId: UUID,
        @RequestParam amount: Int
    ) {
        val details = (SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken).principal as UserDetails
        basketService.addCatalogItemToBasket(details, itemId, amount)
    }

    @PostMapping("/{order_id}/bookings")
    @Operation(summary = "Add catalog item to basket",
            responses = [
                ApiResponse(description = "OK", responseCode = "200"),
                ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
            ],
            security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun bookingFinalization(
        @PathVariable(name = "order_id") orderId: UUID
    ) = BookingDto()
}