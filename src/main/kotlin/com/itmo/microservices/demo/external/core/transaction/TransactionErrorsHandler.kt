package com.itmo.microservices.demo.external.core.transaction

import com.itmo.microservices.demo.external.core.transaction.models.ApiError

class TransactionErrorsHandler
{
    fun getErrorMessage(statusCode: Int, error: ApiError): String
    {
        return "Transaction failed with error code $statusCode and message: \"${error.message}\""
    }
}