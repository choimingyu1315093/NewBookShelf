package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FindFactoryModule {

    @Singleton
    @Provides
    fun provideFindViewModelFactory(
        application: Application,
        findIdUseCase: FindIdUseCase,
        findPwUseCase: FindPwUseCase
    ): FindViewModelFactory {
        return FindViewModelFactory(application, findIdUseCase, findPwUseCase)
    }
}