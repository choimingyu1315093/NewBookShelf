package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class ChatStatusUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String): Flow<Resource<OnlyResultModel>> {
        return bookRepository.chatStatus(accessToken)
    }
}