package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassJoinUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(readingClassJoinData: ReadingClassJoinData): Resource<ReadingClassJoinModel> {
        return bookRepository.readingClassJoin(readingClassJoinData)
    }
}