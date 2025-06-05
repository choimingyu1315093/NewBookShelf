package com.example.newbookshelf.presentation.viewmodel.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.signup.EmailCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.IdCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.NicknameCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.SignupUseCase
import com.example.newbookshelf.domain.usecase.signup.SnsSignupUseCase

class SignupViewModelFactory(
    private val app: Application,
    private val signupUseCase: SignupUseCase,
    private val snsSignupUseCase: SnsSignupUseCase,
    private val idCheckUseCase: IdCheckUseCase,
    private val emailCheckUseCase: EmailCheckUseCase,
    private val nicknameCheckUseCase: NicknameCheckUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignupViewModel::class.java)){
            return SignupViewModel(app, signupUseCase, snsSignupUseCase, idCheckUseCase, emailCheckUseCase, nicknameCheckUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}