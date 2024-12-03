package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.setting.BuyTicketUseCase
import com.example.newbookshelf.domain.usecase.signup.EmailCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.IdCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.NicknameCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.SignupUseCase
import com.example.newbookshelf.domain.usecase.signup.SnsSignupUseCase
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SignupFactoryModule {

    @Singleton
    @Provides
    fun provideSignupViewModelFactory(
        application: Application,
        signupUseCase: SignupUseCase,
        snsSignupUseCase: SnsSignupUseCase,
        idCheckUseCase: IdCheckUseCase,
        emailCheckUseCase: EmailCheckUseCase,
        nicknameCheckUseCase: NicknameCheckUseCase,
        buyTicketUseCase: BuyTicketUseCase
    ): SignupViewModelFactory {
        return SignupViewModelFactory(application, signupUseCase, snsSignupUseCase, idCheckUseCase, emailCheckUseCase, nicknameCheckUseCase, buyTicketUseCase)
    }
}