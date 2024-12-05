package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.db.SearchedBookDAO
import com.example.newbookshelf.data.repository.datasource.BookLocalDataSource
import com.example.newbookshelf.data.repository.datasourceimpl.BookLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(searchedBookDAO: SearchedBookDAO): BookLocalDataSource {
        return BookLocalDataSourceImpl(searchedBookDAO)
    }
}