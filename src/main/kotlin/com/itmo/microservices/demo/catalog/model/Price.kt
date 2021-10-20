package com.itmo.microservices.demo.catalog.model

import javax.persistence.AttributeConverter

data class Price(
    val base: Int,
    val residual: Int,
    val type: Currency
) {
    enum class Currency {
        DOL, RUB, EUR
    }

    class PriceConverter:  AttributeConverter<Price, String> {
        override fun convertToDatabaseColumn(price: Price): String {
            return "${price.base}|${price.residual}|${price.type.name}"
        }

        override fun convertToEntityAttribute(price: String): Price {
            return price.split('|').let {
                Price(it[0].toInt(), it[1].toInt(), Price.Currency.valueOf(it[2]))
            }
        }
    }

    companion object {
        val NONE = Price(-1, -1, Currency.RUB)
    }
}