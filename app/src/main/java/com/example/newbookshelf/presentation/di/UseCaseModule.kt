package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.domain.repository.BookRepository
import com.example.newbookshelf.domain.usecase.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.SnsLoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideIdLoginUseCase(bookRepository: BookRepository): IdLoginUseCase {
        return IdLoginUseCase(bookRepository)
    }

    @Singleton
    @Provides
    fun provideSnsLoginUseCase(bookRepository: BookRepository): SnsLoginUseCase {
        return SnsLoginUseCase(bookRepository)
    }
}