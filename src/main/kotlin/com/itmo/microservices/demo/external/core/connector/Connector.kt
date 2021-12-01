package com.itmo.microservices.demo.external.core.connector

import com.itmo.microservices.demo.external.core.transaction.ExternalServiceResponse
import com.itmo.microservices.demo.external.core.transaction.TransactionErrorsHandler
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class Connector(protected val connectorParameters: ConnectorParameters)
{
    protected val client: HttpClient = HttpClient.newBuilder().build()
    protected val errorsHandler = TransactionErrorsHandler()

    protected inline fun<reified TRequest, reified TResponse> post(endpoint: String, data: TRequest): ExternalServiceResponse<TResponse>
    {
        val body = Json.encodeToString(data);

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${connectorParameters.uri}$endpoint"))
            .setHeader("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return ExternalServiceResponse(response, TResponse::class.java)
    }

    protected inline fun<reified TResponse> get(endpoint: String): ExternalServiceResponse<TResponse>
    {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${connectorParameters.uri}$endpoint"))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return ExternalServiceResponse(response, TResponse::class.java)
    }
}