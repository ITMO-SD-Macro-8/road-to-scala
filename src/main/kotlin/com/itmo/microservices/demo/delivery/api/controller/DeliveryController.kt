package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.api.dto.OrderTimeSlotDto
import com.itmo.microservices.demo.delivery.impl.entity.TimeSlot
import com.itmo.microservices.demo.delivery.impl.service.DeliveryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @PostMapping("/getTimeSlot")
    @Operation(
        summary = "Gets nearest available time slot or NULL",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun getTimeSlot(
        @RequestBody preferredTimeSlot: LocalDate,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): TimeSlot? = deliveryService.getNearestTimeSlot(preferredTimeSlot)

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