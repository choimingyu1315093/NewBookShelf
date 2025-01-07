package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.readingclass.ReadingClassDetailModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassDetailUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(readingClassIdx: Int): Resource<ReadingClassDetailModel> {
        return bookRepository.readingClassDetail(readingClassIdx)
    }
}