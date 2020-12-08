package com.example.hw9navigationregistration.api

import com.google.gson.annotations.SerializedName

data class TokenRequestParams(
    @SerializedName("token")
    val token: String,
)
