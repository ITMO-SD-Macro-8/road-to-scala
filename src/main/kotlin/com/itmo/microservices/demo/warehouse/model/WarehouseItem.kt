package com.itmo.microservices.demo.warehouse.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class WarehouseItem {
    @Id
    @GeneratedValue
    var whiId: UUID? = null
    var wh: Warehouse = Warehouse.NONE
    var whId: Int? = null
    var wAddress: String? = null
    var goodId: Int? = null
}