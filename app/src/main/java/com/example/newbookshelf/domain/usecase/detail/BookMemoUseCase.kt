package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.memo.GetBookMemoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class BookMemoUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, isbn: String, getType: String): Resource<GetBookMemoModel> {
        return bookRepository.bookMemo(accessToken, isbn, getType)
    }
}