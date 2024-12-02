package com.example.newbookshelf.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginModel (
    val data: LoginResultData,
    val message: String,
    val result: Boolean,
    val statusCode: Int
)

data class LoginResultData(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("user_idx")
    val userIdx: Int
)