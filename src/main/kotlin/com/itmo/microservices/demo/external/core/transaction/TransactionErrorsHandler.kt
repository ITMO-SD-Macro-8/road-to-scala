package com.itmo.microservices.demo.external.core.transaction

import com.itmo.microservices.demo.external.core.transaction.models.ApiError

class TransactionErrorsHandler
{
    fun handle(statusCode: Int, error: ApiError)
    {
        println("Transaction failed with error code $statusCode and message: \"${error.message}\"")
    }
}