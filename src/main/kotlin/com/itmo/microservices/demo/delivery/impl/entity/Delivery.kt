package com.itmo.microservices.demo.delivery.impl.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Delivery(
    @Id
    val id: UUID = UUID.randomUUID(),
    val orderId: UUID,
    val warehouseId: UUID,
    var status: String,
    var timeSlotId: UUID,
    var address: String
) {

    override fun toString(): String {
        return "Delivery(id=$id, orderId=$orderId, warehouseId=$warehouseId, status=$status, timeSlotId=$timeSlotId, address=$address)"
    }

}