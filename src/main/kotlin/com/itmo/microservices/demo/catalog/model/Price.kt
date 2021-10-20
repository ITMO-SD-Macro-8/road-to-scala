package com.itmo.microservices.demo.catalog.model

data class Price(
    val base: Int,
    val residual: Int,
    val type: Currency
) {
    enum class Currency {
        DOL, RUB, EUR
    }

    companion object {
        val NONE = Price(-1, -1, Currency.RUB)
    }
}