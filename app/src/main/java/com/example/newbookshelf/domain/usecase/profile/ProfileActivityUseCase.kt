package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.profile.ActivityModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class ProfileActivityUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String, userIdx: Int): Flow<Resource<ActivityModel>> {
        return bookRepository.profileActivity(accessToken, userIdx)
    }
}