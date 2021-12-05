package com.itmo.microservices.demo.orders.api.model

enum class OrderStatus(val type: Int) {
    COLLECTING(0),
    DISCARD(1),
    BOOKED(2),
    PAID(3),
    SHIPPING(4),
    REFUND(5),
    COMPLETED(6);

    override fun toString(): String = name

    companion object {
        fun fromType(type: Int): OrderStatus {
            return when(type) {
                0 -> COLLECTING
                1 -> DISCARD
                2 -> BOOKED
                3 -> PAID
                4 -> SHIPPING
                5 -> REFUND
                6 -> COMPLETED
                else -> throw IllegalArgumentException("Invalid order status = $type")
            }
        }
    }
}