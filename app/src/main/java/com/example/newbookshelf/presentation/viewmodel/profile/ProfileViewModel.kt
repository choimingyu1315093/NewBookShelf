package com.example.newbookshelf.presentation.viewmodel.profile

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.profile.MyBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase
import com.example.newbookshelf.domain.usecase.profile.DescriptionChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.MyBookListUseCase
import com.example.newbookshelf.domain.usecase.profile.MyProfileUseCase
import com.example.newbookshelf.domain.usecase.profile.NicknameChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileActivityUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileMemoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val app: Application,
    private val myProfileUseCase: MyProfileUseCase,
    private val profileActivityUseCase: ProfileActivityUseCase,
    private val profileMemoUseCase: ProfileMemoUseCase,
    private val nickNameChangeUseCase: NicknameChangeUseCase,
    private val descriptionChangeUseCase: DescriptionChangeUseCase,
    private val myBookListUseCase: MyBookListUseCase
): AndroidViewModel(app) {

    var userName = MutableLiveData<String>()
    var userDescription = MutableLiveData<String>()

    fun myProfile() = liveData {
        myProfileUseCase.execute().collect {
            emit(it)
        }
    }

    fun profileActivity(accessToken: String, userId: Int) = liveData {
        profileActivityUseCase.execute("Bearer $accessToken", userId).collect {
            emit(it)
        }
    }

    fun profileMemo(accessToken: String) = liveData {
        profileMemoUseCase.execute("Bearer $accessToken").collect {
            emit(it)
        }
    }

    val nicknameChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun nicknameChange(accessToken: String, nickname: String) = viewModelScope.launch(Dispatchers.IO) {
        nicknameChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                nicknameChangeResult.postValue(Resource.Loading())
                val result = nickNameChangeUseCase.execute("Bearer $accessToken", nickname)
                nicknameChangeResult.postValue(result)
            }
        }catch (e: Exception){
            nicknameChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val descriptionChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun descriptionChange(accessToken: String, description: String) = viewModelScope.launch(Dispatchers.IO) {
        descriptionChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                descriptionChangeResult.postValue(Resource.Loading())
                val result = descriptionChangeUseCase.execute("Bearer $accessToken", description)
                descriptionChangeResult.postValue(result)
            }
        }catch (e: Exception){
            descriptionChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun myBookList(accessToken: String, type: String) = liveData {
        myBookListUseCase.execute("Bearer $accessToken", type).collect {
            emit(it)
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