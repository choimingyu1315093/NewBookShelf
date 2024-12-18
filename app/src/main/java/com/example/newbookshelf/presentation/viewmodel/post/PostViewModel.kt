package com.example.newbookshelf.presentation.viewmodel.post

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase

class PostViewModel(
    private val app: Application,
    private val kakaoSearchPlaceUseCase: KakaoSearchPlaceUseCase,
): AndroidViewModel(app) {

    fun searchPlace(accessToken: String, q: String) = liveData {
        kakaoSearchPlaceUseCase.execute("KakaoAK ${accessToken}", q).collect {
            emit(it)
        }
    }
}