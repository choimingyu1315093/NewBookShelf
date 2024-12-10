package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.memo.DeleteBookMemoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class DeleteBookMemoUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, bookMemoIdx: Int): Resource<DeleteBookMemoModel> {
        return bookRepository.deleteBookMemo(accessToken, bookMemoIdx)
    }
}