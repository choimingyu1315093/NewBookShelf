package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchMoreBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase

class SearchBookViewModelFactory(
    private val app: Application,
    private val searchBookUseCase: SearchBookUseCase,
    private val searchMoreBookUseCase: SearchMoreBookUseCase,
    private val searchedBookUseCase: SearchedBookUseCase,
    private val searchedBookInsertUseCase: SearchedBookInsertUseCase,
    private val searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
    private val searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchBookViewModel::class.java)){
            return SearchBookViewModel(app, searchBookUseCase, searchMoreBookUseCase, searchedBookUseCase, searchedBookInsertUseCase, searchedBookDeleteUseCase, searchedBookAllDeleteUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}