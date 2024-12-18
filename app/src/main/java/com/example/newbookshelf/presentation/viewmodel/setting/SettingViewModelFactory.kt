package com.example.newbookshelf.presentation.viewmodel.setting

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.setting.PasswordChangeUseCase
import com.example.newbookshelf.domain.usecase.setting.TicketLogUseCase
import com.example.newbookshelf.domain.usecase.setting.UpdateUserSettingUseCase
import com.example.newbookshelf.domain.usecase.setting.UserDeleteUseCase
import com.example.newbookshelf.domain.usecase.setting.UserSettingUseCase
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModel

class SettingViewModelFactory(
    private val app: Application,
    private val userSettingUseCase: UserSettingUseCase,
    private val updateUserSettingUseCase: UpdateUserSettingUseCase,
    private val ticketLogUseCase: TicketLogUseCase,
    private val passwordChangeUseCase: PasswordChangeUseCase,
    private val userDeleteUseCase: UserDeleteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(app, userSettingUseCase, updateUserSettingUseCase, ticketLogUseCase, passwordChangeUseCase, userDeleteUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}