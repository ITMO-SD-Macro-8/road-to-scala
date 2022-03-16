package com.itmo.microservices.demo.delivery.impl.entity

import com.itmo.microservices.demo.delivery.api.model.DeliveryInfoRecordApiModel
import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import com.itmo.microservices.demo.orders.impl.entity.OrderEntity
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class DeliveryEntity(
    @Id val id: UUID = UUID.randomUUID(),
    @OneToOne val order: OrderEntity,
    val timeslot: Int,
    val status: DeliverySubmissionOutcome,
    val attempts: Int,
    val transactionId: UUID,
    val submissionStartedTime: Long,
    val submittedTime: Long
)
{
    override fun equals(other: Any?): Boolean
    {
        return if (other == null || other !is DeliveryEntity) false else id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    fun toDeliveryInfoModel(): DeliveryInfoRecordApiModel
    {
        return DeliveryInfoRecordApiModel(outcome = status, attempts = attempts, submittedTime = submittedTime, submissionStartedTime = submissionStartedTime, transactionId = transactionId)
    }
}