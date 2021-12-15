package com.itmo.microservices.demo.finlog.api.service

import com.itmo.microservices.demo.finlog.api.model.UserAccountFinancialLogRecordDto
import java.security.Principal
import java.util.*

interface FinlogService {
    fun operations(principal: Principal): List<UserAccountFinancialLogRecordDto>
    fun operationsWithOrder(principal: Principal, orderId: UUID): List<UserAccountFinancialLogRecordDto>
}