package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.api.dto.OrderTimeSlotDto
import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.delivery.impl.service.DeliveryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {
    @GetMapping("/slots")
    @Operation(
        summary = "Получение возможных сейчас слотов доставки",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getTimeSlot(
        @RequestParam number: Int
    ): List<Int> = deliveryService.getPossibleTimeSlots(number)

    @PostMapping("/reserveTimeSlot")
    @Operation(
        summary = "Reserves time slot",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun reserveTimeSlot(
        @RequestBody model: OrderTimeSlotDto,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): Boolean = deliveryService.reserveTimeSlot(model.order, model.timeSlot)
}