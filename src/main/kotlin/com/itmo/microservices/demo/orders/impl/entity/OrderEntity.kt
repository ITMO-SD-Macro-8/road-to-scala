package com.itmo.microservices.demo.orders.impl.entity

import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class OrderEntity(
    @Id val id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.ORDINAL) val status: OrderStatus = OrderStatus.COLLECTING,
    @Column(name = "creation_time") val creationTime: Instant = Instant.now(),
    @OneToMany val positions: MutableSet<OrderPositionEntity> = hashSetOf(),
    @ManyToOne val owner: UserEntity
) {

    fun toApiModel() = OrderApiModel(
        id = id,
        status = status,
        timeCreated = creationTime.toEpochMilli(),
        itemsMap = positions.associate { it.catalogItemId to it.amount }
    )

    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is OrderEntity) false else id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}