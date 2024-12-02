package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import retrofit2.Response

interface BookRemoteDataSource {

    suspend fun login(loginData: LoginData): Response<LoginModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Response<LoginModel>
}