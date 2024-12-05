package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.domain.repository.BookRepository

class SearchedBookDeleteUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(searchedBook: SearchedBook){
        bookRepository.delete(searchedBook)
    }
}