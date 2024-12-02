package com.example.newbookshelf.domain.usecase

import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class SnsLoginUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(snsLoginData: SnsLoginData): Resource<LoginModel>{
        return bookRepository.snsLogin(snsLoginData)
    }
}