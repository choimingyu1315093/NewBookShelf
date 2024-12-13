package com.example.newbookshelf.presentation.di

import com.example.newbookshelf.presentation.view.chat.adapter.ChatListAdapter
import com.example.newbookshelf.presentation.view.chat.adapter.ChatMessageAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.MemoAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.ReviewAdapter
import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NotificationAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookTitleAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchMoreBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import com.example.newbookshelf.presentation.view.map.adapter.NearBookAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileActiveAdapter
import com.example.newbookshelf.presentation.view.profile.adapter.ProfileMemoAdapter
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

    @Singleton
    @Provides
    fun provideNotificationAdapter(): NotificationAdapter {
        return NotificationAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchBookTitleAdapter(): SearchBookTitleAdapter {
        return SearchBookTitleAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchBookAdapter(): SearchBookAdapter {
        return SearchBookAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchMoreBookAdapter(): SearchMoreBookAdapter {
        return SearchMoreBookAdapter()
    }

    @Singleton
    @Provides
    fun provideReviewAdapter(): ReviewAdapter {
        return ReviewAdapter()
    }

    @Singleton
    @Provides
    fun provideMemoAdapter(): MemoAdapter {
        return MemoAdapter()
    }

    @Singleton
    @Provides
    fun provideProfileActiveAdapter(): ProfileActiveAdapter {
        return ProfileActiveAdapter()
    }

    @Singleton
    @Provides
    fun provideProfileMemoAdapter(): ProfileMemoAdapter {
        return ProfileMemoAdapter()
    }

    @Singleton
    @Provides
    fun provideNearBookAdapter(): NearBookAdapter {
        return NearBookAdapter()
    }

    @Singleton
    @Provides
    fun provideChatListAdapter(): ChatListAdapter {
        return ChatListAdapter()
    }

    @Singleton
    @Provides
    fun provideChatMessageAdapter() : ChatMessageAdapter {
        return ChatMessageAdapter()
    }
}