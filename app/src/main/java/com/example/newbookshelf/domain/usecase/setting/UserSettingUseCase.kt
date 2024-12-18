package com.example.newbookshelf.domain.usecase.setting

import com.example.newbookshelf.data.model.setting.UserSettingModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class UserSettingUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String): Flow<Resource<UserSettingModel>> {
        return bookRepository.userSetting(accessToken)
    }
}