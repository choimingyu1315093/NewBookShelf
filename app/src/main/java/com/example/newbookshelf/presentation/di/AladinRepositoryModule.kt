package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.repository.AladinBookRepositoryImpl
import com.example.newbookshelf.data.repository.BookRepositoryImpl
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import com.example.newbookshelf.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AladinRepositoryModule {

    @Singleton
    @Provides
    fun provideAladinBookRepository(aladinBookRemoteDataSource: AladinBookRemoteDataSource): AladinBookRepository {
        return AladinBookRepositoryImpl(aladinBookRemoteDataSource)
    }
}