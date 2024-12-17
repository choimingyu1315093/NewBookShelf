package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.data.api.AladinApiService
import com.example.newbookshelf.data.api.KakaoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object KakaoAppModule {

    @KakaoRetrofit
    @Singleton
    @Provides
    fun provideKakaoRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @KakaoRetrofit
    @Singleton
    @Provides
    fun provideKakaoApiService(@KakaoRetrofit retrofit: Retrofit): KakaoApiService {
        return retrofit.create(KakaoApiService::class.java)
    }
}