package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class PostDeleteUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(postIdx: Int): Resource<OnlyResultModel> {
        return bookRepository.postDelete(postIdx)
    }
}