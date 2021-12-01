package com.itmo.microservices.demo.external.core.transaction

import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.itmo.microservices.demo.external.core.transaction.models.ApiError
import org.springframework.http.HttpStatus
import java.net.http.HttpResponse


class ExternalServiceResponse<T>(response: HttpResponse<String>, classType: Class<T>)
{
    companion object {
        val gson = Gson()
    }

    val statusCode: Int

    val result: T?
    val error: ApiError?

    val hasError: Boolean get() = error != null

    init
    {
        statusCode = response.statusCode()

        if (statusCode == HttpStatus.OK.value())
        {
            result = gson.fromJson(response.body(), classType)
            error = null
        }
        else
        {
            error = gson.fromJson(response.body(), ApiError::class.java)
            result = null
        }
    }
}