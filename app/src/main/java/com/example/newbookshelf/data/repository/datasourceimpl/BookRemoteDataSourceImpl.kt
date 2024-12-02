package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import retrofit2.Response

class BookRemoteDataSourceImpl(private val apiService: ApiService): BookRemoteDataSource {

    override suspend fun login(loginData: LoginData): Response<LoginModel> {
        return apiService.login(loginData)
    }

    override suspend fun snsLogin(snsLoginData: SnsLoginData): Response<LoginModel> {
        return apiService.snsLogin(snsLoginData)
    }
}