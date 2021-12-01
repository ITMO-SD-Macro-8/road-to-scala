package com.itmo.microservices.demo.external.delivery

import com.itmo.microservices.demo.external.core.connector.ExternalServiceConnector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.ExternalServiceRequestException
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import com.itmo.microservices.demo.external.core.transaction.TransactionStatus

class DeliveryServiceConnector(connectorParameters: ConnectorParameters)
    : ExternalServiceConnector(connectorParameters)
{
    private val pollingTimeoutInMs: Long = 10

    /**
     * Polling
     */
    override fun makeTransaction(endpoint: String, transactionRequest: TransactionRequest)
    {
        var result: TransactionResponse

        try
        {
            result = post(endpoint, transactionRequest)
        }
        catch (e: ExternalServiceRequestException)
        {
            println(e.message)
            return
        }

        println("id is " + result.id)

        var triesCount = 0
        while(result.status == TransactionStatus.PENDING)
        {
            Thread.sleep(pollingTimeoutInMs)

            try
            {
                result = get("transactions/${result.id}")
            }
            catch (e: ExternalServiceRequestException)
            {
                println(e.message)
                return
            }

            triesCount++
        }

        println("Count $triesCount")
        println(result)
    }
}