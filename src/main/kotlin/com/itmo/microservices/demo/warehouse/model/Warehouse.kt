package com.itmo.microservices.demo.warehouse.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Warehouse {
    @Id
    @GeneratedValue
    var wId: UUID? = null
    var wAddress: String? = null
    var Capasity: Int? = null
    var CurrentItemsCount: Int? = null

    constructor()

    companion object {
        val NONE = Warehouse()
    }
}