package com.example.newbookshelf.domain.usecase.setting

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.setting.PasswordChangeData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class PasswordChangeUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(passwordChangeData: PasswordChangeData): Resource<OnlyResultModel> {
        return bookRepository.passwordChange(passwordChangeData)
    }
}