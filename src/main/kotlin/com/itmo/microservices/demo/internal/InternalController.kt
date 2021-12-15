package com.itmo.microservices.demo.internal

import com.itmo.microservices.demo.delivery.api.model.DeliveryInfoRecordApiModel
import com.itmo.microservices.demo.items.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.items.api.model.CatalogItemApiModel
import com.itmo.microservices.demo.items.api.service.CatalogItemService
import com.itmo.microservices.demo.orders.api.model.BookingLogRecordApiModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/_internal")
class InternalController(
    private val catalogItemService: CatalogItemService
) {

    @PostMapping("/catalogItem")
    @Operation(
        summary = "Добавление товаров в каталог",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun addCatalogItem(
        @RequestBody request: AddCatalogItemRequest,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): CatalogItemApiModel = catalogItemService.addCatalogItem(request).toApiModel()

    @GetMapping("/bookingHistory/{bookingId}")
    @Operation(
        summary = "Получить список забронированных товаров по bookingId",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun getBookingRecordsByBookingId(
        @PathVariable bookingId: UUID,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): List<BookingLogRecordApiModel> = listOf(BookingLogRecordApiModel())

    @GetMapping("/deliveryLog/{orderId}")
    @Operation(
        summary = "Получить список забронированных товаров по bookingId",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun getDeliveryHistoryByOrderId(
        @PathVariable orderId: UUID,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): List<DeliveryInfoRecordApiModel> = listOf(DeliveryInfoRecordApiModel())
}