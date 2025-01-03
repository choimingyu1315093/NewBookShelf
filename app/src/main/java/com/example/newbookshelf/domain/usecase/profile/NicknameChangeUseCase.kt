package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class NicknameChangeUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(nickname: String): Resource<OnlyResultModel> {
        return bookRepository.nicknameChange(nickname)
    }
}