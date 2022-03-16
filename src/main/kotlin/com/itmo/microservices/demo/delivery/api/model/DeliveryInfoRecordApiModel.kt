package com.itmo.microservices.demo.delivery.api.model

import java.util.*

data class DeliveryInfoRecordApiModel(
    val outcome: DeliverySubmissionOutcome,
    val preparedTime: Long = 0,
    val attempts: Int,
    val submittedTime: Long,
    val transactionId: UUID,
    val submissionStartedTime: Long
)