package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.profile.TopBookData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class TopBookChangeUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(topBookData: TopBookData): Resource<OnlyResultModel> {
        return bookRepository.topBookChange(topBookData)
    }
}