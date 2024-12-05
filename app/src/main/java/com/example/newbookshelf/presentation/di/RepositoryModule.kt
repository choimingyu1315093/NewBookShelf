package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.repository.BookRepositoryImpl
import com.example.newbookshelf.data.repository.datasource.BookLocalDataSource
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource, bookLocalDataSource: BookLocalDataSource): BookRepository {
        return BookRepositoryImpl(bookRemoteDataSource, bookLocalDataSource)
    }
}