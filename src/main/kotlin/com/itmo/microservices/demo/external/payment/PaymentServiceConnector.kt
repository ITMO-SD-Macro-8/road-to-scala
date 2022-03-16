package com.itmo.microservices.demo.external.payment

import com.itmo.microservices.demo.external.core.connector.ExternalServiceConnector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.ExternalServiceRequestException
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse

class PaymentServiceConnector(connectorParameters: ConnectorParameters)
    : ExternalServiceConnector(connectorParameters)
{
    /**
     * Request-Response
     */
    override fun makeTransaction(endpoint: String, transactionRequest: TransactionRequest): TransactionResponse
    {
        return post(endpoint, transactionRequest)
    }
}