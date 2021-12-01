package com.itmo.microservices.demo.external.core.connector

import java.net.URL

abstract class ConnectorParameters(
    protected val url: URL
)