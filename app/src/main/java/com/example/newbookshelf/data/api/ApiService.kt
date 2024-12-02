package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("authentications/sign-in")
    suspend fun login(
        @Body loginData: LoginData
    ): Response<LoginModel>

    @POST("authentications/sign-in")
    suspend fun snsLogin(
        @Body snsLoginData: SnsLoginData
    ): Response<LoginModel>
}