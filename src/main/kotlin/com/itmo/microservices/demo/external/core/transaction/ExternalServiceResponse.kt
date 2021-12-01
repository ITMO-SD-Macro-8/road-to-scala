package com.itmo.microservices.demo.external.core.transaction

import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.itmo.microservices.demo.external.core.transaction.models.ApiError
import org.springframework.http.HttpStatus
import java.net.http.HttpResponse


class ExternalServiceResponse<T>(response: HttpResponse<String>)
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
            result = gson.fromJson(response.body(), object: TypeToken<T>() {}.type)
            error = null
        }
        else
        {
            error = gson.fromJson(response.body(), ApiError::class.java)
            result = null
        }
    }
}