package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.profile.MyBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class MyBookListUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String, readType: String): Flow<Resource<MyBookModel>> {
        return bookRepository.myBookList(accessToken, readType)
    }
}