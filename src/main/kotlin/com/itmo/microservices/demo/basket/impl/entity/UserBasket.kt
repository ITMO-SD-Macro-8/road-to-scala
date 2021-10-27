package com.itmo.microservices.demo.basket.impl.entity


import java.util.*
import javax.persistence.*

@Entity
data class BasketEntity @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    val userId: UUID = UUID.randomUUID(),
    @OneToMany val entries: MutableSet<BasketEntry> = hashSetOf()
)


@Entity
data class BasketEntry @JvmOverloads constructor(
    @Id @GeneratedValue val id: Int = -1,
    val basketEntityId: UUID = UUID.randomUUID(),
    val catalogItemId: UUID = UUID.randomUUID(),
    val amount: Int = -1
)

