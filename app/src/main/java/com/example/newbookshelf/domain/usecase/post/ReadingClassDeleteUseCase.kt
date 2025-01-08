package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassDeleteUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(readingClassIdx: Int): Resource<OnlyResultModel> {
        return bookRepository.readingClassDelete(readingClassIdx)
    }
}