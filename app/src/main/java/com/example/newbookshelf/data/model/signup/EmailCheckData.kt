package com.example.newbookshelf.data.model.signup

import com.google.gson.annotations.SerializedName

data class EmailCheckData(
    @SerializedName("user_email")
    val userEmail: String
)
