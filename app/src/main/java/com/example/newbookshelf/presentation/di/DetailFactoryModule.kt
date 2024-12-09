package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DetailFactoryModule {

    @Singleton
    @Provides
    fun provideDetailViewModelFactory(
        application: Application,
        detailBookUseCase: DetailBookUseCase,
        addMyBookUseCase: AddMyBookUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(application, detailBookUseCase, addMyBookUseCase)
    }
}