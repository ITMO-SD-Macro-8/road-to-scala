package com.itmo.microservices.demo.external.payment

import com.itmo.microservices.demo.external.core.connector.Connector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse

class PaymentServiceConnector(connectorParameters: ConnectorParameters)
    : Connector(connectorParameters)
{
    /**
     * Request-Response
     */
    fun makeTransaction(transactionRequest: TransactionRequest, endpoint: String)
    {
        val response = post<TransactionRequest, TransactionResponse>(endpoint, transactionRequest)

        if (response.hasError)
        {
            errorsHandler.handle(response.statusCode, response.error!!)
            return
        }

        var result = response.result!!

        println(result.id)
        println(result.cost)
        println(result.delta)
        println(result.status)
        println(result.submitTime)
        println(result.completedTime)
    }
}