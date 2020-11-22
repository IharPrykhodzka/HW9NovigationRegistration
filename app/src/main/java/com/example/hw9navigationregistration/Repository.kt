package com.example.hw9navigationregistration

import com.example.hw9navigationregistration.api.API
import com.example.hw9navigationregistration.api.AuthRequestParams
import com.example.hw9navigationregistration.api.RegistrationRequestParams
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    // Ленивое создание Retrofit экземпляра
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://netology-back-end-post-hw-8.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Ленивое создание API
    private val API: API by lazy {
        retrofit.create(
            com.example.hw9navigationregistration.api.API::class.java
        )
    }
    suspend fun authenticate(login: String, password: String) =
        API.authenticate(
            AuthRequestParams(login, password)
        )

    suspend fun registration(login: String, password: String) =
        API.registrable(
            RegistrationRequestParams(login, password)
        )
}