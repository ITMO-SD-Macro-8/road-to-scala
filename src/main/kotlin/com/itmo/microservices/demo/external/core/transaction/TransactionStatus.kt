package com.itmo.microservices.demo.external.core.transaction

enum class TransactionStatus {
    SUCCESS, // успешная транзакция
    FAILURE, // неуспешная транзакция
    PENDING // транзакция в очереди на обработку
}