package com.example.newbookshelf.data.model.signup

data class SignupModel(
    val result: Boolean,
    val data: SignupModelData,
)

data class SignupModelData(
    val access_token: String
)