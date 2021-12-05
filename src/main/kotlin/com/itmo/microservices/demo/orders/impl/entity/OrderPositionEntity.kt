package com.itmo.microservices.demo.orders.impl.entity

import java.util.*
import javax.persistence.*

@Entity(name = "order_position_entity")
data class OrderPositionEntity(
        @Id val id: UUID = UUID.randomUUID(),
        @Column(name = "catalog_item_id") val catalogItemId: UUID,
        @ManyToOne(targetEntity = OrderEntity::class) val order: OrderEntity,
        val amount: Int
) {

    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is OrderPositionEntity) false else id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}