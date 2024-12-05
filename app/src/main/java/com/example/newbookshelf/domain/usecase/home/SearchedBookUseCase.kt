package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class SearchedBookUseCase(private val bookRepository: BookRepository) {

    fun execute(): Flow<List<SearchedBook>>{
        return bookRepository.searchedBook()
    }
}