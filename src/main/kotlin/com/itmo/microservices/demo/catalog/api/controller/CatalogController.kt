package com.itmo.microservices.demo.catalog.api.controller

import com.itmo.microservices.demo.catalog.api.model.AddCatalogItemRequest
import com.itmo.microservices.demo.catalog.api.service.CatalogService
import com.itmo.microservices.demo.catalog.impl.entity.CatalogItem
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class CatalogController(
    private val catalogService: CatalogService
) {
    @GetMapping
    @Operation(
        summary = "Returns catalog items",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun all(@RequestParam available: Boolean): List<CatalogItem> = catalogService.allCatalogItems(available)

    @PutMapping
    @Operation(
        summary = "Put catalog item",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun addCatalogItem(
        @RequestBody request: AddCatalogItemRequest,
        @Parameter(hidden = true) @AuthenticationPrincipal user: UserDetails
    ) = catalogService.addCatalogItem(request)
}