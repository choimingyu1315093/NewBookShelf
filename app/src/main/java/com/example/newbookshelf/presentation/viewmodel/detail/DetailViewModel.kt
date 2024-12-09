package com.example.newbookshelf.presentation.viewmodel.detail

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.detail.AddMyBookData
import com.example.newbookshelf.data.model.detail.AddMyBookModel
import com.example.newbookshelf.data.model.detail.DetailBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val app: Application,
    private val detailBookUseCase: DetailBookUseCase,
    private val addMyBookUseCase: AddMyBookUseCase
): AndroidViewModel(app) {

    val detailBookResult = MutableLiveData<Resource<DetailBookModel>>()
    fun detailBook(accessToken: String, isbn: String) = viewModelScope.launch(Dispatchers.IO) {
        detailBookResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                detailBookResult.postValue(Resource.Loading())
                val result = detailBookUseCase.execute("Bearer $accessToken", isbn)
                detailBookResult.postValue(result)
            }
        }catch (e: Exception){
            detailBookResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val addMyBookResult = MutableLiveData<Resource<AddMyBookModel>>()
    fun addMyBook(accessToken: String, addMyBookData: AddMyBookData) = viewModelScope.launch(Dispatchers.IO) {
        addMyBookResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addMyBookResult.postValue(Resource.Loading())
                val result = addMyBookUseCase.execute("Bearer $accessToken", addMyBookData)
                addMyBookResult.postValue(result)
            }
        }catch (e: Exception){
            addMyBookResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }
}