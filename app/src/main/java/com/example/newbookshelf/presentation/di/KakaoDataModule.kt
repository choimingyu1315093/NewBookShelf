package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.api.AladinApiService
import com.example.newbookshelf.data.api.KakaoApiService
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.data.repository.datasourceimpl.AladinBookRemoteDataSourceImpl
import com.example.newbookshelf.data.repository.datasourceimpl.KakaoDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KakaoDataModule {

    @Singleton
    @Provides
    fun provideKakaoDataSource(@KakaoRetrofit kakaoApiService: KakaoApiService): KakaoDataSource {
        return KakaoDataSourceImpl(kakaoApiService)
    }
}