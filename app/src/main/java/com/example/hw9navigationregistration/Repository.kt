package com.example.hw9navigationregistration

import com.example.hw9navigationregistration.api.InjectAuthTokenInterceptor
import com.example.hw9navigationregistration.api.API
import com.example.hw9navigationregistration.api.AuthRequestParams
import com.example.hw9navigationregistration.api.RegistrationRequestParams
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Repository {
    // Ленивое создание Retrofit экземпляра
    private var retrofit: Retrofit=
        Retrofit.Builder()
            .baseUrl("https://netology-back-end-post-hw-8.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                    .readTimeout(1, TimeUnit.MINUTES) // read timeout
                    .build()
            )
            .build()




    // Добавление interceptor-ов в retrofit клиент. Во все последующие запросы будет добавляться токен
    // и они будут логироваться

    fun createRetrofitWithAuth(authToken: String) {

       retrofit = Retrofit.Builder()
            .baseUrl("https://netology-back-end-post-hw-8.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(InjectAuthTokenInterceptor(authToken))
                    .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                    .readTimeout(1, TimeUnit.MINUTES) // read timeout
                    .build()
            )
            .build()

        API = retrofit.create(com.example.hw9navigationregistration.api.API::class.java)

    }





    // Ленивое создание API
    private var API: API =
        retrofit.create(
            com.example.hw9navigationregistration.api.API::class.java
        )


    suspend fun authenticate(login: String, password: String) =
        API.authenticate(
            AuthRequestParams(login, password)
        )

    suspend fun registration(login: String, password: String) =
        API.registrable(
            RegistrationRequestParams(login, password)
        )

    suspend fun getAllPosts() =
        API.getAllPosts()
}