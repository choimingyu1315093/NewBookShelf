package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.DetailBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class DetailBookUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, bookIsbn: String): Resource<DetailBookModel> {
        return bookRepository.detailBook(accessToken, bookIsbn)
    }
}