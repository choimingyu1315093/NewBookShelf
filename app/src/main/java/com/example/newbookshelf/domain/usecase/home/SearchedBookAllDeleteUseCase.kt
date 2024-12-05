package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.domain.repository.BookRepository

class SearchedBookAllDeleteUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(){
        bookRepository.allDelete()
    }
}