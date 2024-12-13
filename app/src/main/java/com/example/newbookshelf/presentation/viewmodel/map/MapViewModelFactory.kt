package com.example.newbookshelf.presentation.viewmodel.map

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.chat.CreateChatroomUseCase
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase

class MapViewModelFactory(
    private val app: Application,
    private val wishBookHaveUserUseCase: WishBookHaveUserUseCase,
    private val createChatroomUseCase: CreateChatroomUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(app, wishBookHaveUserUseCase, createChatroomUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}