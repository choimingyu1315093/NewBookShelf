package com.example.newbookshelf.domain.usecase.signup

import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class EmailCheckUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(emailCheckData: EmailCheckData): Resource<CheckModel> {
        return bookRepository.emailCheck(emailCheckData)
    }
}