package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.order.api.service.OrderFinalizationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/finalize")
class OrderFinalizationController @Autowired constructor(
    private val orderFinalizationService: OrderFinalizationService
) {
    @PostMapping
    @Operation(
        summary = "Finalizes order",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun finalizeOrder(@Parameter(hidden = true) @AuthenticationPrincipal userDetails: UserDetails)
        = orderFinalizationService.finalizeOrder(userDetails)
}