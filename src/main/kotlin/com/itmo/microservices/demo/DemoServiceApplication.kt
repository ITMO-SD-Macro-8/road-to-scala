package com.itmo.microservices.demo

import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.connector.ConnectorUser
import com.itmo.microservices.demo.external.core.transaction.models.TransactionRequest
import com.itmo.microservices.demo.external.delivery.DeliveryServiceConnector
import com.itmo.microservices.demo.external.payment.PaymentServiceConnector
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoServiceApplication

fun main(args: Array<String>) {
    runApplication<DemoServiceApplication>(*args)
}

// request-response
fun paymentRequest()
{
    val user = ConnectorUser("225133b4-87c3-45ed-b83c-522c8c8db7c2");
    val settings = ConnectorParameters("http://77.234.215.138:30027/", user);

    val connector = PaymentServiceConnector(settings);

    val transaction = TransactionRequest(user.clientSecret)
    connector.makeTransaction(transaction, "transactions")
}

// polling
fun deliveryRequest()
{
    val user = ConnectorUser("678391c3-dd29-46b9-b96e-d692699a0c66");
    val settings = ConnectorParameters("http://77.234.215.138:30027/", user);

    val connector = DeliveryServiceConnector(settings);

    val transaction = TransactionRequest(user.clientSecret)
    connector.makeTransaction(transaction, "transactions")
}