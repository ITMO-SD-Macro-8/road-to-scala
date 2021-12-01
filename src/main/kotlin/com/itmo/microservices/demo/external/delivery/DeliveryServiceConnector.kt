package com.itmo.microservices.demo.external.delivery

import com.itmo.microservices.demo.external.core.connector.Connector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus

class DeliveryServiceConnector(connectorParameters: ConnectorParameters)
    : Connector(connectorParameters)
{
    private val pollingTimeoutInMs: Long = 10

    /**
     * Polling
     */
    fun makeTransaction(transactionRequest: TransactionRequest, endpoint: String)
    {
        var response = post<TransactionRequest, TransactionResponse>(endpoint, transactionRequest)

        if (response.hasError)
        {
            errorsHandler.handle(response.statusCode, response.error!!)
            return
        }

        var result = response.result!!

        println("id is " + result.id)

        var triesCount = 0
        while(result.status == TransactionStatus.PENDING)
        {
            Thread.sleep(pollingTimeoutInMs)

            response = get("transactions/${result.id}")

            if (response.hasError)
            {
                errorsHandler.handle(response.statusCode, response.error!!)
                return
            }

            result = response.result!!

            triesCount++
        }

        println("Count $triesCount")
        println(response)
    }
}