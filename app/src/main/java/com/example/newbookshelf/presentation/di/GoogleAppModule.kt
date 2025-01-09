package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.BuildConfig
import com.example.newbookshelf.data.api.GoogleMapApiService
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
object GoogleAppModule {

    @GoogleRetrofit
    @Singleton
    @Provides
    fun provideGoogleRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GOOGLE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GoogleRetrofit
    @Singleton
    @Provides
    fun provideGoogleApiService(@GoogleRetrofit retrofit: Retrofit): GoogleMapApiService {
        return retrofit.create(GoogleMapApiService::class.java)
    }
}