package com.itmo.microservices.demo.external.payment

import com.itmo.microservices.demo.external.core.connector.Connector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.TransactionResponse

class PaymentServiceConnector(connectorParameters: ConnectorParameters)
    : Connector(connectorParameters)
{
    /**
     * Request-Response
     */
    fun makeTransaction(transactionRequest: TransactionRequest, endpoint: String)
    {
        val response = post<TransactionRequest, TransactionResponse>(endpoint, transactionRequest)

        println(response.id)
        println(response.cost)
        println(response.delta)
        println(response.status)
        println(response.submitTime)
        println(response.completedTime)
    }
}