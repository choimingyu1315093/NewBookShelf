package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapFactoryModule {

    @Singleton
    @Provides
    fun provideMapViewModelFactory(
        app: Application,
        wishBookHaveUserUseCase: WishBookHaveUserUseCase
    ): MapViewModelFactory {
        return MapViewModelFactory(app, wishBookHaveUserUseCase)
    }
}