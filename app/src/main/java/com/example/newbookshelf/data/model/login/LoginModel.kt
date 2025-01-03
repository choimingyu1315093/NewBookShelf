package com.example.newbookshelf.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginModel (
    val result: Boolean,
    val data: AccessToken
)

data class AccessToken (
    val access_token: String
)