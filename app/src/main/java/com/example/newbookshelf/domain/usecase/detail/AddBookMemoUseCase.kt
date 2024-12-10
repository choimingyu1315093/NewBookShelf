package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.memo.AddBookMemoData
import com.example.newbookshelf.data.model.detail.memo.AddBookMemoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddBookMemoUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, addBookMemoData: AddBookMemoData): Resource<AddBookMemoModel> {
        return bookRepository.addBookMemo(accessToken, addBookMemoData)
    }
}