package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassDetailModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassFinishData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassFinishUserCase(private val bookRepository: BookRepository) {

    suspend fun execute(readingClassFinishData: ReadingClassFinishData): Resource<OnlyResultModel> {
        return bookRepository.readingClassFinish(readingClassFinishData)
    }
}