package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.chat.DeleteChatroomUseCase
import com.example.newbookshelf.presentation.viewmodel.chat.ChatViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ChatFactoryModule {

    @Suppress
    @Provides
    fun provideChatViewModelFactory(
        application: Application,
        deleteChatroomUseCase: DeleteChatroomUseCase
    ): ChatViewModelFactory {
        return ChatViewModelFactory(application, deleteChatroomUseCase)
    }
}