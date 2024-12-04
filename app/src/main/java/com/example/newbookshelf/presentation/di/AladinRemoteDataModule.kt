package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.api.AladinApiService
import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.repository.datasourceimpl.AladinBookRemoteDataSourceImpl
import com.example.newbookshelf.data.repository.datasourceimpl.BookRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AladinRemoteDataModule {

    @Singleton
    @Provides
    fun provideAladinBookRemoteDataSource(@AladinRetrofit aladinApiService: AladinApiService): AladinBookRemoteDataSource {
        return AladinBookRemoteDataSourceImpl(aladinApiService)
    }
}
