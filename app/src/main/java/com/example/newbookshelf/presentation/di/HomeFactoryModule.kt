package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HomeFactoryModule {

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        application: Application,
        weekBestsellerUseCase: WeekBestsellerUseCase,
        newBestsellerUseCase: NewBestsellerUseCase,
        attentionBestsellerUseCase: AttentionBestsellerUseCase
    ): HomeViewModelFactory{
        return HomeViewModelFactory(application, weekBestsellerUseCase, newBestsellerUseCase, attentionBestsellerUseCase)
    }
}
