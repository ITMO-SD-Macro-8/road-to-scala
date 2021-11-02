package com.itmo.microservices.demo.payment.api

import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.payment.entity.Payment
import com.itmo.microservices.demo.payment.models.OrderPayment
import com.itmo.microservices.demo.payment.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService
) {

    @PostMapping("/make")
    @Operation(
        summary = "Makes payment",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun make(
        @RequestBody model: OrderModel,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ): Payment = paymentService.makePayment(model)

    @PostMapping("/rollback")
    @Operation(
        summary = "Rollbacks payment",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun rollback(
        @RequestBody request: OrderPayment,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ) = paymentService.rollbackPayment(request.order, request.payment)

    @PostMapping("/refund")
    @Operation(
        summary = "Refunds payment",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun refund(
        @RequestBody request: OrderPayment,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ) = paymentService.makeRefund(request.order, request.payment)
}