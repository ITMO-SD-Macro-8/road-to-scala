package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {
    // @Coomman
    @GetMapping("/slots")
    @Operation(
        summary = "Получение возможных слотов доставки",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getTimeSlot(
        @RequestParam number: Int // number of slots we want to get
    ): List<Int> = deliveryService.getPossibleTimeslots(number)
}