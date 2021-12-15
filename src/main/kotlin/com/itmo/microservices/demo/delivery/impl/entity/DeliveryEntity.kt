package com.itmo.microservices.demo.delivery.impl.entity

import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import java.util.*
import javax.persistence.*

@Entity
data class DeliveryEntity @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    @OneToOne val order: OrderEntity,
    val timeslot: Int = 0,
    val status: DeliverySubmissionOutcome = DeliverySubmissionOutcome.SUCCESS,
    val attempts: Int = 1,
    val transactionId: UUID = UUID.randomUUID(),
    val submissionStartedTime: Long = 1,
    val submittedTime: Long = 2
    ) {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is DeliveryEntity) false else id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}