package com.itmo.microservices.demo

import com.itmo.microservices.demo.external.core.connector.ConnectorParameters
import com.itmo.microservices.demo.external.core.connector.ConnectorUser
import com.itmo.microservices.demo.external.core.transaction.TransactionRequest
import com.itmo.microservices.demo.external.delivery.DeliveryServiceConnector
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoServiceApplication

fun main(args: Array<String>) {
    //runApplication<DemoServiceApplication>(*args)

    val user = ConnectorUser("225133b4-87c3-45ed-b83c-522c8c8db7c2");
    val settings = ConnectorParameters("http://77.234.215.138:30027/", user);

    val connector = DeliveryServiceConnector(settings);

    val transaction = TransactionRequest(user.clientSecret)
    connector.makeTransaction(transaction, "transactions")
}