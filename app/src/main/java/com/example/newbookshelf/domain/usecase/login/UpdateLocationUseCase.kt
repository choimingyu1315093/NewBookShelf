package com.example.newbookshelf.domain.usecase.login

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class UpdateLocationUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, updateLocationData: UpdateLocationData): Resource<OnlyResultModel> {
        return bookRepository.updateLocation(accessToken, updateLocationData)
    }
}