package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchMoreBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.login.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.login.SnsLoginUseCase
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase
import com.example.newbookshelf.presentation.viewmodel.home.SearchBookViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SearchFactoryModule {

    @Singleton
    @Provides
    fun provideSearchBookViewModelFactory(
        application: Application,
        searchBookUseCase: SearchBookUseCase,
        searchMoreBookUseCase: SearchMoreBookUseCase,
        searchedBookUseCase: SearchedBookUseCase,
        searchedBookInsertUseCase: SearchedBookInsertUseCase,
        searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
        searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
    ): SearchBookViewModelFactory {
        return SearchBookViewModelFactory(application, searchBookUseCase, searchMoreBookUseCase, searchedBookUseCase, searchedBookInsertUseCase, searchedBookDeleteUseCase, searchedBookAllDeleteUseCase)
    }
}