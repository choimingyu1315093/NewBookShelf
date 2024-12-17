package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.repository.AladinBookRepositoryImpl
import com.example.newbookshelf.data.repository.KakaoRepositoryImpl
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import com.example.newbookshelf.domain.repository.KakaoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KakaoRepositoryModule {

    @Singleton
    @Provides
    fun provideKakaoRepository(kakaoDataSource: KakaoDataSource): KakaoRepository {
        return KakaoRepositoryImpl(kakaoDataSource)
    }
}
