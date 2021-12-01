package com.itmo.microservices.demo.orders.impl.entity

import com.itmo.microservices.demo.orders.api.model.OrderApiModel
import com.itmo.microservices.demo.orders.api.model.OrderStatus
import com.itmo.microservices.demo.users.impl.entity.UserEntity
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
data class OrderEntity @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    @Convert(converter = StatusConverter::class) val status: OrderStatus = OrderStatus.COLLECTING,
    @CreatedDate @Column(name = "creation_time") val creationTime: Instant? = null,
    @OneToMany val positions: MutableSet<OrderPositionEntity> = hashSetOf(),
    @ManyToOne val owner: UserEntity,
) {

    fun toApiModel() = OrderApiModel(
        id = id,
        status = status,
        timeCreated = creationTime?.toEpochMilli() ?: 0,
        itemsMap = positions.associate { it.catalogItemId to it.amount }
    )

    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is OrderEntity) false else id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    class StatusConverter: AttributeConverter<OrderStatus, Int> {
        override fun convertToDatabaseColumn(status: OrderStatus): Int = status.type

        override fun convertToEntityAttribute(status: Int): OrderStatus = OrderStatus.fromType(status)
    }
}