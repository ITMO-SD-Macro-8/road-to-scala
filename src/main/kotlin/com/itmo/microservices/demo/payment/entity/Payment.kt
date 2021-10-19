package com.itmo.microservices.demo.payment.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Payment{

    @Id
    @GeneratedValue
    var id: UUID? = null
    var price: Double? = null
    var status: String? = null // TODO

    constructor()

    constructor(id: UUID?, price: Double?, status: String?) {
        this.id = id
        this.price = price
        this.status = status
    }

    override fun toString(): String {
        return "Payment(id=$id, price=$price, status=$status)"
    }

}