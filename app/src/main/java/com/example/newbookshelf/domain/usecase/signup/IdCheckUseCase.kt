package com.example.newbookshelf.domain.usecase.signup

import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class IdCheckUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(id: String): Resource<CheckModel>{
        return bookRepository.idCheck(id)
    }
}