package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.readingclass.ReadingClassModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(searchWord: String, filterType: String, limit: Int, currentPage: Int): Resource<ReadingClassModel> {
        return bookRepository.readingClassList(searchWord, filterType, limit, currentPage)
    }
}