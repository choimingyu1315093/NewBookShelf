package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.profile.DescriptionChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.MyBookListUseCase
import com.example.newbookshelf.domain.usecase.profile.MyProfileUseCase
import com.example.newbookshelf.domain.usecase.profile.NicknameChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileActivityUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileMemoUseCase
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProfileFactoryModule {

    @Singleton
    @Provides
    fun provideProfileViewModelFactory(
        app: Application,
        myProfileUseCase: MyProfileUseCase,
        profileActivityUseCase: ProfileActivityUseCase,
        profileMemoUseCase: ProfileMemoUseCase,
        nicknameChangeUseCase: NicknameChangeUseCase,
        descriptionChangeUseCase: DescriptionChangeUseCase,
        myBookListUseCase: MyBookListUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(app, myProfileUseCase, profileActivityUseCase, profileMemoUseCase, nicknameChangeUseCase, descriptionChangeUseCase, myBookListUseCase)
    }
}