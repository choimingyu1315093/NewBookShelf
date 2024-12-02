package com.example.newbookshelf.data.model.login

import com.google.gson.annotations.SerializedName

data class SnsLoginData(
    @SerializedName("fcm_token")
    val fcmToken: String,

    @SerializedName("login_type")
    val loginType: String,

    @SerializedName("user_id")
    val userId: String
)
