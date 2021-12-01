package com.itmo.microservices.demo.users.api.controller

import com.itmo.microservices.demo.users.api.model.*
import com.itmo.microservices.demo.users.api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    @Operation(
        summary = "Создание пользователя",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun registerNewUser(@RequestBody request: RegistrationRequest): UserApiModel
        = userService.registerUser(request).toApiModel()

    @GetMapping("/{user_id}")
    @Operation(
        summary = "Получение пользователя",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "User not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getAccountData(@PathVariable("user_id") userId: UUID): UserApiModel
        = userService.getAccountData(userId).toApiModel()
}