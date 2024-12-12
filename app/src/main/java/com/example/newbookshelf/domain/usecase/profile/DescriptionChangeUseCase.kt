package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class DescriptionChangeUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, description: String): Resource<OnlyResultModel> {
        return bookRepository.descriptionChange(accessToken, description)
    }
}