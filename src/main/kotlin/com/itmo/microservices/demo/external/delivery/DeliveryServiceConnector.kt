package com.itmo.microservices.demo.external.delivery

import com.itmo.microservices.demo.external.core.connector.Connector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.TransactionResponse
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus

class DeliveryServiceConnector(connectorParameters: ConnectorParameters)
    : Connector(connectorParameters)
{
    val pollingTimeoutInMs: Long = 100

    /**
     * Polling
     */
    fun makeTransaction(transactionRequest: TransactionRequest, endpoint: String)
    {
        var response = post<TransactionRequest, TransactionResponse>(endpoint, transactionRequest)

        println("id is " + response.id)

        var triesCount = 0
        while(response.status == TransactionStatus.PENDING)
        {
            Thread.sleep(pollingTimeoutInMs)

            response = get<TransactionResponse>("transactions/${response.id}")

            triesCount++
        }

        println("Count $triesCount")
        println(response)
    }
}