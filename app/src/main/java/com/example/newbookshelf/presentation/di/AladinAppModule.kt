package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.data.api.AladinApiService
import com.example.newbookshelf.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AladinAppModule {

    @AladinRetrofit
    @Singleton
    @Provides
    fun provideAladinRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ALADIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AladinRetrofit
    @Singleton
    @Provides
    fun provideAladinApiService(@AladinRetrofit retrofit: Retrofit): AladinApiService {
        return retrofit.create(AladinApiService::class.java)
    }
}