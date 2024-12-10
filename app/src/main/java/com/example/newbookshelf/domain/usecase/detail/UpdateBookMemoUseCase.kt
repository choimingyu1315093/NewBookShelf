package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.memo.UpdateBookMemoData
import com.example.newbookshelf.data.model.detail.memo.UpdateBookMemoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class UpdateBookMemoUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, bookMemoIdx: Int, updateBookMemoData: UpdateBookMemoData): Resource<UpdateBookMemoModel> {
        return bookRepository.updateBookMemo(accessToken, bookMemoIdx, updateBookMemoData)
    }
}