package com.itmo.microservices.demo.users.api.controller

import com.itmo.microservices.demo.users.api.model.*
import com.itmo.microservices.demo.users.api.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    @Operation(
        summary = "Register new user",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun register(@RequestBody request: RegistrationRequest) = userService.registerUser(request)

    @PostMapping("/restore_pwd")
    @Operation(
        summary = "Request restoring user's password",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun requestPasswordRestore(@RequestBody request: RestorePasswordRequest)
        = userService.requestPasswordRestore(request)

    @PutMapping("/verify_pwd")
    @Operation(
        summary = "Verify user's new password",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun verifyNewPassword(@RequestBody request: VerifyNewPasswordRequest)
        = userService.verifyNewPassword(request)

    @GetMapping("/{user_id}")
    @Operation(
        summary = "Get current user info",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "User not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getAccountData(@PathVariable("user_id") userId: UUID): UserDto =
            userService.getAccountData(userId)
}