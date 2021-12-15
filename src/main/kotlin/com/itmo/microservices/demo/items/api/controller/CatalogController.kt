package com.itmo.microservices.demo.items.api.controller

import com.itmo.microservices.demo.items.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.items.api.model.CatalogItemApiModel
import com.itmo.microservices.demo.items.api.service.CatalogItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class CatalogController(private val catalogItemService: CatalogItemService) {
    @GetMapping
    @Operation(
        summary = "Returns catalog items",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun all(@RequestParam available: Boolean): List<CatalogItemApiModel>
        = catalogItemService.allCatalogItems(available).map { it.toApiModel() }
}