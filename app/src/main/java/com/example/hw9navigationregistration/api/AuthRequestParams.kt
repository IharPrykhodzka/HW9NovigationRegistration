package com.example.hw9navigationregistration.api

import com.google.gson.annotations.SerializedName

data class AuthRequestParams(
    @SerializedName("userName")
    val username: String,
    @SerializedName("password")
    val password: String
)