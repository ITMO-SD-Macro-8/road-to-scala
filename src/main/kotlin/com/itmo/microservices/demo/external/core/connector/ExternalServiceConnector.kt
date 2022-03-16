package com.itmo.microservices.demo.external.core.connector

import com.itmo.microservices.demo.external.core.transaction.ExternalServiceRequestException
import com.itmo.microservices.demo.external.core.transaction.ExternalServiceResponse
import com.itmo.microservices.demo.external.core.transaction.TransactionErrorsHandler
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.core.transaction.models.TransactionResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class ExternalServiceConnector(protected val connectorParameters: ConnectorParameters)
{
    protected val client: HttpClient = HttpClient.newBuilder().build()
    protected val errorsHandler = TransactionErrorsHandler()

    protected inline fun<reified TRequest, reified TResponse> post(endpoint: String, data: TRequest): TResponse
    {
        val body = Json.encodeToString(data);

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${connectorParameters.uri}$endpoint"))
            .setHeader("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return getResult(response)
    }

    protected inline fun<reified TResponse> get(endpoint: String): TResponse
    {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${connectorParameters.uri}$endpoint"))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return getResult(response)
    }

    protected inline fun <reified TResponse> getResult(httpResponse: HttpResponse<String>): TResponse
    {
        val response = ExternalServiceResponse(httpResponse, TResponse::class.java)

        if (!response.hasError) return response.result!!

        val errorMessage = errorsHandler.getErrorMessage(response.statusCode, response.error!!)

        throw ExternalServiceRequestException(errorMessage)
    }

    abstract fun makeTransaction(endpoint: String, transactionRequest: TransactionRequest): TransactionResponse
}