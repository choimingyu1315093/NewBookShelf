package com.example.newbookshelf.presentation.viewmodel.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase
import com.example.newbookshelf.domain.usecase.profile.DescriptionChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.MyBookListUseCase
import com.example.newbookshelf.domain.usecase.profile.MyProfileUseCase
import com.example.newbookshelf.domain.usecase.profile.NicknameChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileActivityUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileMemoUseCase
import com.example.newbookshelf.domain.usecase.profile.TopBookChangeUseCase
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class ProfileViewModelFactory(
    private val app: Application,
    private val myProfileUseCase: MyProfileUseCase,
    private val profileActivityUseCase: ProfileActivityUseCase,
    private val profileMemoUseCase: ProfileMemoUseCase,
    private val nickNameChangeUseCase: NicknameChangeUseCase,
    private val descriptionChangeUseCase: DescriptionChangeUseCase,
    private val topBookChangeUseCase: TopBookChangeUseCase,
    private val myBookListUseCase: MyBookListUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(app, myProfileUseCase, profileActivityUseCase, profileMemoUseCase, nickNameChangeUseCase, descriptionChangeUseCase, topBookChangeUseCase, myBookListUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}