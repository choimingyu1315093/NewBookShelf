package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.data.repository.GoogleMapRepositoryImpl
import com.example.newbookshelf.data.repository.KakaoRepositoryImpl
import com.example.newbookshelf.data.repository.datasource.GoogleMapDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.domain.repository.GoogleMapRepository
import com.example.newbookshelf.domain.repository.KakaoRepository
import com.google.android.gms.maps.GoogleMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GoogleRepositoryModule {

    @Singleton
    @Provides
    fun provideGoogleRepository(googleMapDataSource: GoogleMapDataSource): GoogleMapRepository {
        return GoogleMapRepositoryImpl(googleMapDataSource)
    }
}