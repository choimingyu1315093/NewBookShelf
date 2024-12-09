package com.example.newbookshelf.presentation.viewmodel.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class DetailViewModelFactory(
    private val app: Application,
    private val detailBookUseCase: DetailBookUseCase,
    private val addMyBookUseCase: AddMyBookUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(app, detailBookUseCase, addMyBookUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}