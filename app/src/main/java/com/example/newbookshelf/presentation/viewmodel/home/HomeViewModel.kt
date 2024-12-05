package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val app: Application,
    private val weekBestsellerUseCase: WeekBestsellerUseCase,
    private val newBestsellerUseCase: NewBestsellerUseCase,
    private val attentionBestsellerUseCase: AttentionBestsellerUseCase,
    private val alarmCountUseCase: AlarmCountUseCase,
    private val searchBookUseCase: SearchBookUseCase,
    private val searchedBookUseCase: SearchedBookUseCase,
    private val searchedBookInsertUseCase: SearchedBookInsertUseCase,
    private val searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
    private val searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
): AndroidViewModel(app) {

    fun weekBestseller(searchTarget: String, categoryId: Int) = liveData {
        weekBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun newBestseller(searchTarget: String, categoryId: Int) = liveData {
        newBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun attentionBestseller(searchTarget: String, categoryId: Int) = liveData {
        attentionBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun alarmCount(accessToken: String) = liveData {
        alarmCountUseCase.execute("Bearer $accessToken").collect {
            emit(it)
        }
    }

    fun getSearchBook(accessToken: String, bookName: String) = liveData {
        searchBookUseCase.execute("Bearer $accessToken", bookName).collect {
            emit(it)
        }
    }

    fun getSearchedBook() = liveData {
        searchedBookUseCase.execute().collect {
            emit(it)
        }
    }

    fun insertSearchedBook(searchedBook: SearchedBook) = viewModelScope.launch {
        searchedBookInsertUseCase.execute(searchedBook)
    }

    fun deleteSearchedBook(searchedBook: SearchedBook) = viewModelScope.launch {
        searchedBookDeleteUseCase.execute(searchedBook)
    }

    fun allDeleteSearchedBook() = viewModelScope.launch {
        searchedBookAllDeleteUseCase.execute()
    }
}