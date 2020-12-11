package com.example.hw9navigationregistration.api

import com.example.hw9navigationregistration.dto.PostResponseDto
import com.example.hw9navigationregistration.model.PostModel
import retrofit2.Response
import retrofit2.http.*

interface API {
    @POST("/api/v1/authentication")
    suspend fun authenticate(@Body authRequestParams: AuthRequestParams): Response<Token>

    @POST("/api/v1/registration")
    suspend fun registrable(@Body registrationRequestParams: RegistrationRequestParams): Response<Token>

    @GET("api/v1/posts")
    suspend fun getAllPosts(): Response<List<PostResponseDto>>

    @POST("api/v1/posts/{id}/likes ")
    suspend fun likedByMe(@Path("id")id: Int):Response<PostModel>

    @DELETE("api/v1/posts/{id}/likes")
    suspend fun cancelMyLike(@Path("id")id: Int):Response<PostModel>
}