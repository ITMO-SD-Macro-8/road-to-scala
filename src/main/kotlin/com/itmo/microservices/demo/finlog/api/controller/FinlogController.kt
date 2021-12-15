package com.itmo.microservices.demo.finlog.api.controller

import com.itmo.microservices.demo.finlog.api.model.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.finlog.api.service.FinlogService
import com.itmo.microservices.demo.orders.api.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/finlog")
class FinlogController (private val finlogService: FinlogService) {

    //TODO @Coomman
    @GetMapping
    @Operation(
        summary = "Получение информации о финансовых операциях с аккаунтом пользователя",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun operations(principal: Principal) = finlogService.operations(principal)

    @GetMapping
    @Operation(
        summary = "Получение информации о финансовых операциях с аккаунтом пользователя и заказами",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun operationsWithOrder(principal: Principal, @RequestParam(name = "order_id") orderId: UUID) = finlogService.operationsWithOrder(principal, orderId)
}