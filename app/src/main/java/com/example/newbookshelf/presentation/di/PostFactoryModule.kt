package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.chat.CreateChatroomUseCase
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PostFactoryModule {

    @Singleton
    @Provides
    fun providePostViewModelFactory(
        app: Application,
        kakaoSearchPlaceUseCase: KakaoSearchPlaceUseCase
    ): PostViewModelFactory {
        return PostViewModelFactory(app, kakaoSearchPlaceUseCase)
    }
}