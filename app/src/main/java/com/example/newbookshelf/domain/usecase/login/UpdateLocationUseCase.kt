package com.example.newbookshelf.domain.usecase.login

import android.util.Log
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class UpdateLocationUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(updateLocationData: UpdateLocationData): Resource<OnlyResultModel> {
        return bookRepository.updateLocation(updateLocationData)
    }
}