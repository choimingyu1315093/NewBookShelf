package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.domain.usecase.setting.PasswordChangeUseCase
import com.example.newbookshelf.domain.usecase.setting.TicketLogUseCase
import com.example.newbookshelf.domain.usecase.setting.UpdateUserSettingUseCase
import com.example.newbookshelf.domain.usecase.setting.UserDeleteUseCase
import com.example.newbookshelf.domain.usecase.setting.UserSettingUseCase
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SettingFactoryModule {

    @Singleton
    @Provides
    fun provideSettingViewModelFactory(
        app: Application,
        userSettingUseCase: UserSettingUseCase,
        updateUserSettingUseCase: UpdateUserSettingUseCase,
        ticketLogUseCase: TicketLogUseCase,
        passwordChangeUseCase: PasswordChangeUseCase,
        userDeleteUseCase: UserDeleteUseCase
    ): SettingViewModelFactory {
        return SettingViewModelFactory(app, userSettingUseCase, updateUserSettingUseCase, ticketLogUseCase, passwordChangeUseCase, userDeleteUseCase)
    }
}