package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.SnsLoginUseCase
import com.example.newbookshelf.presentation.viewmodel.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FactoryModule {

    @Singleton
    @Provides
    fun provideLoginViewModelFactory(
        application: Application,
        loginUseCase: IdLoginUseCase,
        snsLoginUseCase: SnsLoginUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(application, loginUseCase, snsLoginUseCase)
    }
}