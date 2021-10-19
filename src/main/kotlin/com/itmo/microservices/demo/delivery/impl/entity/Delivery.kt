package com.itmo.microservices.demo.delivery.impl.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Delivery {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var orderId: UUID? = null
    var warehouseId: UUID? = null // TODO
    var status: String? = null // TODO
    var timeSlotId: UUID? = null
    var address: String? = null

    constructor()

    constructor(id: UUID?, orderId: UUID?, warehouseId: UUID?, status: String?, timeSlotId: UUID?, address: String?) {
        this.id = id
        this.orderId = orderId
        this.warehouseId = warehouseId
        this.status = status
        this.timeSlotId = timeSlotId
        this.address = address
    }

    override fun toString(): String {
        return "Delivery(id=$id, orderId=$orderId, warehouseId=$warehouseId, status=$status, timeSlotId=$timeSlotId, address=$address)"
    }

}