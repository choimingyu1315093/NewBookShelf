package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class MyProfileUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(): Resource<MyProfileModel> {
        return bookRepository.myProfile()
    }
}