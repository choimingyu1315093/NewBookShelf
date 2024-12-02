package com.example.newbookshelf.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("fcm_token")
    val fcmToken: String,

    @SerializedName("login_type")
    val loginType: String,

    @SerializedName("user_id")
    val userId: String,

    @SerializedName("user_password")
    val userPassword: String
)