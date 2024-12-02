package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.util.Resource

interface BookRepository {

    suspend fun login(loginData: LoginData): Resource<LoginModel>
    suspend fun snsLogin(snsLoginData: SnsLoginData): Resource<LoginModel>
}