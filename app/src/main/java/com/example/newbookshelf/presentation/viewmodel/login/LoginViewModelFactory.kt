package com.example.newbookshelf.presentation.viewmodel.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.login.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.login.SnsLoginUseCase
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase

class LoginViewModelFactory(
    private val app: Application,
    private val loginUseCase: IdLoginUseCase,
    private val snsLoginUseCase: SnsLoginUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(app, loginUseCase, snsLoginUseCase, updateLocationUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}