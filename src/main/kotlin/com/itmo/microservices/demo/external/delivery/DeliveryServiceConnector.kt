package com.itmo.microservices.demo.external.delivery

import com.itmo.microservices.demo.external.core.connector.Connector
import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.transaction.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.TransactionResponse

import kotlinx.serialization.*
import kotlinx.serialization.json.*

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class DeliveryServiceConnector(connectorParameters: ConnectorParameters)
    : Connector(connectorParameters)
{
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