package com.example.newbookshelf.domain.usecase

import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class IdLoginUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(loginData: LoginData): Resource<LoginModel> {
        return bookRepository.login(loginData)
    }
}