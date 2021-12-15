package com.itmo.microservices.demo.delivery.api.model

import java.util.*

data class DeliveryInfoRecordApiModel(
    val outcome: DeliverySubmissionOutcome = DeliverySubmissionOutcome.SUCCESS,
    val preparedTime: Long = 0L,
    val attempts: Int = 0,
    val submittedTime: Long = 0,
    val transactionId: UUID = UUID.randomUUID(),
    val submissionStartedTime: Long = 0
)