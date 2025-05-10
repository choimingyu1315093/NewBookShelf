package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmListUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmOneDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmStatusUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.ChatStatusUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchMoreBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HomeFactoryModule {

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        application: Application,
        weekBestsellerUseCase: WeekBestsellerUseCase,
        newBestsellerUseCase: NewBestsellerUseCase,
        attentionBestsellerUseCase: AttentionBestsellerUseCase,
        chatStatusUseCase: ChatStatusUseCase,
        alarmCountUseCase: AlarmCountUseCase,
        alarmStatusUseCase: AlarmStatusUseCase,
        alarmListUseCase: AlarmListUseCase,
        alarmAllDeleteUseCase: AlarmAllDeleteUseCase,
        alarmOneDeleteUseCase: AlarmOneDeleteUseCase,
        searchBookUseCase: SearchBookUseCase,
        searchMoreBookUseCase: SearchMoreBookUseCase,
        searchedBookUseCase: SearchedBookUseCase,
        searchedBookInsertUseCase: SearchedBookInsertUseCase,
        searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
        searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
    ): HomeViewModelFactory{
        return HomeViewModelFactory(application, weekBestsellerUseCase, newBestsellerUseCase, attentionBestsellerUseCase, chatStatusUseCase, alarmCountUseCase, alarmStatusUseCase, alarmListUseCase, alarmAllDeleteUseCase, alarmOneDeleteUseCase, searchBookUseCase, searchMoreBookUseCase, searchedBookUseCase, searchedBookInsertUseCase, searchedBookDeleteUseCase, searchedBookAllDeleteUseCase)
    }
}
