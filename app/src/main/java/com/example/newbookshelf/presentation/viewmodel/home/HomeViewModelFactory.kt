package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModel

class HomeViewModelFactory(
    private val app: Application,
    private val weekBestsellerUseCase: WeekBestsellerUseCase,
    private val newBestsellerUseCase: NewBestsellerUseCase,
    private val attentionBestsellerUseCase: AttentionBestsellerUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(app, weekBestsellerUseCase, newBestsellerUseCase, attentionBestsellerUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}