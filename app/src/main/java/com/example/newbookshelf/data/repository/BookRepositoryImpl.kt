package com.example.newbookshelf.data.repository

import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import retrofit2.Response

class BookRepositoryImpl(private val bookRemoteDataSource: BookRemoteDataSource): BookRepository {

    override suspend fun login(loginData: LoginData): Resource<LoginModel> {
        return responseToResource(bookRemoteDataSource.login(loginData))
    }

    override suspend fun snsLogin(snsLoginData: SnsLoginData): Resource<LoginModel> {
        return responseToResource(bookRemoteDataSource.snsLogin(snsLoginData))
    }

    private fun responseToResource(response: Response<LoginModel>): Resource<LoginModel>{
        if(response.isSuccessful){
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}