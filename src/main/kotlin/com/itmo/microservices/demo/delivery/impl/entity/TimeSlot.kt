package com.itmo.microservices.demo.delivery.impl.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class TimeSlot {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var startTime: Date? = null
    var endTime: Date? = null

    var reservedCount: Int? = null
    var reservedMax: Int? = null

    constructor()

    constructor(id: UUID?, startTime: Date?, endTime: Date?, reservedCount: Int?, reservedMax: Int?) {
        this.id = id
        this.startTime = startTime
        this.endTime = endTime
        this.reservedCount = reservedCount
        this.reservedMax = reservedMax
    }

    override fun toString(): String {
        return "TimeSlot(id=$id, startTime=$startTime, endTime=$endTime, reservedCount=$reservedCount, reservedMax=$reservedMax)"
    }
}