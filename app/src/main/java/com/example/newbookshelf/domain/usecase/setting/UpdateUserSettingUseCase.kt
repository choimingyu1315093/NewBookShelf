package com.example.newbookshelf.domain.usecase.setting

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.setting.UpdateUserSettingData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class UpdateUserSettingUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(updateUserSettingData: UpdateUserSettingData): Resource<OnlyResultModel> {
        return bookRepository.updateUserSetting(updateUserSettingData)
    }
}