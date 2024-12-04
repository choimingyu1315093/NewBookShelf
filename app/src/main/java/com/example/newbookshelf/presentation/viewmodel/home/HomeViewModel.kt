package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase

class HomeViewModel(
    private val app: Application,
    private val weekBestsellerUseCase: WeekBestsellerUseCase,
    private val newBestsellerUseCase: NewBestsellerUseCase,
    private val attentionBestsellerUseCase: AttentionBestsellerUseCase
): AndroidViewModel(app) {

    fun weekBestseller(searchTarget: String, categoryId: Int) = liveData {
        weekBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun newBestseller(searchTarget: String, categoryId: Int) = liveData {
        newBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun attentionBestseller(searchTarget: String, categoryId: Int) = liveData {
        attentionBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }
}