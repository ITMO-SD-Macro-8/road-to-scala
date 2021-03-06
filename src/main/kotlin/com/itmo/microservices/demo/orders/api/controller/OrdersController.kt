package com.itmo.microservices.demo.orders.api.controller

import com.itmo.microservices.demo.common.exception.BadRequestException
import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.orders.api.service.OrderService
import com.itmo.microservices.demo.orders.api.model.BookingDto
import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/orders")
class OrdersController(private val orderService: OrderService,
                       private val deliveryService: DeliveryService,
                       private val paymentService: PaymentService
) {

    // @Aroize

    @GetMapping("/{order_id}")
    @Operation(
        summary = "Получение заказа",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getOrder(@PathVariable(name = "order_id") orderId: UUID): OrderApiModel = orderService.getOrderInfo(orderId)

    @PostMapping
    @Operation(
        summary = "Создание нового заказа",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun createOrder(principal: Principal): OrderApiModel = orderService.createNewOrder(principal)


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
        if (amount == 0)
            throw BadRequestException("You can't add 0 items to order")
        orderService.putCatalogItemToOrder(orderId, itemId, amount)
    }

    // Author Coomman

    @PostMapping("/{order_id}/bookings")
    @Operation(summary = "Оформление (финализация/бронирование) заказа",
            responses = [
                ApiResponse(description = "OK", responseCode = "200"),
                ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
            ],
            security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun arrangeBooking(
        @PathVariable(name = "order_id") orderId: UUID
    ) : BookingDto = orderService.arrangeBooking(orderId)

    @PostMapping("/{order_id}/delivery")
    @Operation(summary = "Установление желаемого времени доставки",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun setPreferredTimeSlot(
        @PathVariable(name = "order_id") orderId: UUID,
        @RequestParam(name = "slot_in_sec") slotInSec: Int
    ) : BookingDto = deliveryService.setPreferredTimeSlot(orderId, slotInSec)

    @PostMapping("/{order_id}/payment")
    @Operation(summary = "Оплата заказа",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun pay(principal: Principal,
        @PathVariable(name = "order_id") orderId: UUID
    ) = paymentService.paymentProceed(principal, orderId)
}