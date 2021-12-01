package com.itmo.microservices.demo.external.core.connector

import com.itmo.microservices.demo.external.core.transaction.TransactionResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class Connector(protected val connectorParameters: ConnectorParameters)
{
    val client: HttpClient = HttpClient.newBuilder().build()

    protected inline fun<reified TRequest, reified TResponse> post(endpoint: String, data: TRequest): TResponse
    {
        val body = Json.encodeToString(data);

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${connectorParameters.uri}$endpoint"))
            .setHeader("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return Json.decodeFromString(response.body())
    }
}