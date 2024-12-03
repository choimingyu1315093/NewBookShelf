package com.example.newbookshelf.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginModel (
    val result: Boolean,
    val data: LoginResultData
)

data class LoginResultData(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("user_idx")
    val userIdx: Int,

    @SerializedName("login_type")
    val loginType: String
)