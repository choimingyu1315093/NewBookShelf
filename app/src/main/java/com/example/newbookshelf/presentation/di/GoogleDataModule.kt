package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.api.GoogleMapApiService
import com.example.newbookshelf.data.api.KakaoApiService
import com.example.newbookshelf.data.repository.datasource.GoogleMapDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.data.repository.datasourceimpl.GoogleMapDataSourceImpl
import com.example.newbookshelf.data.repository.datasourceimpl.KakaoDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GoogleDataModule {

    @Singleton
    @Provides
    fun provideGoogleDataSource(@GoogleRetrofit googleMapApiService: GoogleMapApiService): GoogleMapDataSource {
        return GoogleMapDataSourceImpl(googleMapApiService)
    }
}