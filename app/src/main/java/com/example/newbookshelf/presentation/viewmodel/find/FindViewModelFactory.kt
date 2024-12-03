package com.example.newbookshelf.presentation.viewmodel.find

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase

class FindViewModelFactory(
    private val app: Application,
    private val findIdUseCase: FindIdUseCase,
    private val findPwUseCase: FindPwUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FindViewModel::class.java)){
            return FindViewModel(app, findIdUseCase, findPwUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}