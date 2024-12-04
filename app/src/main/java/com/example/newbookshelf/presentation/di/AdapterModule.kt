package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AdapterModule {

    @Singleton
    @Provides
    fun provideWeekBestsellerAdapter(): WeekBestsellerAdapter {
        return WeekBestsellerAdapter()
    }

    @Singleton
    @Provides
    fun provideNewBestsellerAdapter(): NewBestsellerAdapter {
        return NewBestsellerAdapter()
    }

    @Singleton
    @Provides
    fun provideAttentionBestsellerAdapter(): AttentionBestsellerAdapter {
        return AttentionBestsellerAdapter()
    }
}