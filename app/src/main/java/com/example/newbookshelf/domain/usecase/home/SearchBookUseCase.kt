package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class SearchBookUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, bookName: String): Resource<SearchBookModel> {
        return bookRepository.searchBook(accessToken, bookName)
    }
}