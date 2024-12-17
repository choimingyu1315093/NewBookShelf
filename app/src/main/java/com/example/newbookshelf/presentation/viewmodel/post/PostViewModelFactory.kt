package com.example.newbookshelf.presentation.viewmodel.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel

class PostViewModelFactory(
    private val app: Application,
    private val kakaoSearchPlaceUseCase: KakaoSearchPlaceUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(app, kakaoSearchPlaceUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}