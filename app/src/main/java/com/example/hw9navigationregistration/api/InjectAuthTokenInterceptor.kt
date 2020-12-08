package com.example.hw9navigationregistration.api

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

const val AUTH_TOKEN_HEADER = "Authorization"



class InjectAuthTokenInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val requestWithToken = originalRequest.newBuilder()
            .header(AUTH_TOKEN_HEADER, "Bearer $authToken")
            .build()

        val response = chain.proceed(requestWithToken)

        if (!response.isSuccessful) {
            checkResponseCode(response.code)
        }

        return response
    }

    private fun checkResponseCode(code: Int) {
        when (code) {
            401 -> throw Exception("Без Токена")
            //TODO *** -> throw PostNotFoundException()
        }
    }
}
