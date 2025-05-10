package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookData
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class SearchMoreBookUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(searchMoreBookData: SearchMoreBookData): Resource<SearchMoreBookModel> {
        return bookRepository.searchMoreBook(searchMoreBookData)
    }
}