package com.example.newbookshelf.data.model.signup

data class SignupData(
    val fcm_token: String,
    val login_type: String,
    val user_email: String,
    val user_id: String,
    val user_name: String,
    val user_password: String
)