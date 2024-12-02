package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.repository.datasourceimpl.BookRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideBookRemoteDataSource(apiService: ApiService): BookRemoteDataSource {
        return BookRemoteDataSourceImpl(apiService)
    }
}