package com.example.newbookshelf.presentation.viewmodel.find

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.find.FindIdUseCase
import com.example.newbookshelf.domain.usecase.find.FindPwUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindViewModel(
    private val app: Application,
    private val findIdUseCase: FindIdUseCase,
    private val findPwUseCase: FindPwUseCase
): AndroidViewModel(app) {

    val id = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val findResult = MutableLiveData<Resource<FindModel>>()

    fun findId(findIdData: FindIdData) = viewModelScope.launch(Dispatchers.IO) {
        findResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                findResult.postValue(Resource.Loading())
                val result = findIdUseCase.execute(findIdData)
                findResult.postValue(result)
            }
        }catch (e: Exception){
            findResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun findPw(findPwData: FindPwData) = viewModelScope.launch(Dispatchers.IO) {
        findResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                findResult.postValue(Resource.Loading())
                val result = findPwUseCase.execute(findPwData)
                findResult.postValue(result)
            }
        }catch (e: Exception){
            findResult.postValue(Resource.Error(e.message.toString()))
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