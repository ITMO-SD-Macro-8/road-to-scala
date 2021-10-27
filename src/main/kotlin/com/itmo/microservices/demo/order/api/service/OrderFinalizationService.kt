package com.itmo.microservices.demo.order.api.service

import org.springframework.security.core.userdetails.UserDetails

interface OrderFinalizationService {
    fun finalizeOrder(userDetails: UserDetails)
}