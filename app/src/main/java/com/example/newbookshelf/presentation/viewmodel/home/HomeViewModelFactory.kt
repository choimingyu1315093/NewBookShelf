package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.home.AlarmAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmListUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmOneDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel

class HomeViewModelFactory(
    private val app: Application,
    private val weekBestsellerUseCase: WeekBestsellerUseCase,
    private val newBestsellerUseCase: NewBestsellerUseCase,
    private val attentionBestsellerUseCase: AttentionBestsellerUseCase,
    private val alarmCountUseCase: AlarmCountUseCase,
    private val alarmListUseCase: AlarmListUseCase,
    private val alarmAllDeleteUseCase: AlarmAllDeleteUseCase,
    private val alarmOneDeleteUseCase: AlarmOneDeleteUseCase,
    private val searchBookUseCase: SearchBookUseCase,
    private val searchedBookUseCase: SearchedBookUseCase,
    private val searchedBookInsertUseCase: SearchedBookInsertUseCase,
    private val searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
    private val searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(app, weekBestsellerUseCase, newBestsellerUseCase, attentionBestsellerUseCase, alarmCountUseCase, alarmListUseCase, alarmAllDeleteUseCase, alarmOneDeleteUseCase, searchBookUseCase, searchedBookUseCase, searchedBookInsertUseCase, searchedBookDeleteUseCase, searchedBookAllDeleteUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}