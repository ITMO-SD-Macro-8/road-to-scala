package com.itmo.microservices.demo.external.core.transaction

import javax.management.OperationsException

class ExternalServiceRequestException(message: String): OperationsException(message)