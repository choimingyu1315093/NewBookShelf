package com.example.newbookshelf.presentation.viewmodel.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
import kotlinx.coroutines.flow.collect

class MapViewModel(
    private val app: Application,
    private val wishBookHaveUserUseCase: WishBookHaveUserUseCase
): AndroidViewModel(app) {

    fun wishBookHaveUser(accessToken: String) = liveData {
        wishBookHaveUserUseCase.execute("Bearer $accessToken").collect {
            emit(it)
        }
    }
}