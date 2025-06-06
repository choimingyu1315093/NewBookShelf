package com.example.newbookshelf.presentation.viewmodel.profile

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.model.profile.TopBookData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.profile.DescriptionChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.MyBookListUseCase
import com.example.newbookshelf.domain.usecase.profile.MyProfileUseCase
import com.example.newbookshelf.domain.usecase.profile.NicknameChangeUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileActivityUseCase
import com.example.newbookshelf.domain.usecase.profile.ProfileMemoUseCase
import com.example.newbookshelf.domain.usecase.profile.ReadingStatisticsUseCase
import com.example.newbookshelf.domain.usecase.profile.TopBookChangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val app: Application,
    private val myProfileUseCase: MyProfileUseCase,
    private val profileActivityUseCase: ProfileActivityUseCase,
    private val profileMemoUseCase: ProfileMemoUseCase,
    private val nickNameChangeUseCase: NicknameChangeUseCase,
    private val descriptionChangeUseCase: DescriptionChangeUseCase,
    private val topBookChangeUseCase: TopBookChangeUseCase,
    private val myBookListUseCase: MyBookListUseCase,
    private val readingStatisticsUseCase: ReadingStatisticsUseCase
): AndroidViewModel(app) {

    var userName = MutableLiveData<String>()
    var userDescription = MutableLiveData<String>()
    var userBestSeller = MutableLiveData<ArrayList<String>>()
    var userBestSellerList = MutableLiveData<ArrayList<String>>()
    var userBestSellerIsbnList = MutableLiveData<ArrayList<String>>()

    val myProfileInfo = MutableSharedFlow<Resource<MyProfileModel>>()
    fun myProfile() = viewModelScope.launch(Dispatchers.IO) {
        myProfileInfo.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                myProfileInfo.emit(Resource.Loading())
                val result = myProfileUseCase.execute()
                myProfileInfo.emit(result)
            }
        }catch (e: Exception){
            myProfileInfo.emit(Resource.Error(e.message.toString()))
        }
    }

    fun profileActivity(userId: Int) = liveData {
        profileActivityUseCase.execute(userId).collect {
            emit(it)
        }
    }

    fun profileMemo() = liveData {
        profileMemoUseCase.execute().collect {
            emit(it)
        }
    }

    val nicknameChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun nicknameChange(nickname: String) = viewModelScope.launch(Dispatchers.IO) {
        nicknameChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                nicknameChangeResult.postValue(Resource.Loading())
                val result = nickNameChangeUseCase.execute(nickname)
                nicknameChangeResult.postValue(result)
            }
        }catch (e: Exception){
            nicknameChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val descriptionChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun descriptionChange(description: String) = viewModelScope.launch(Dispatchers.IO) {
        descriptionChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                descriptionChangeResult.postValue(Resource.Loading())
                val result = descriptionChangeUseCase.execute(description)
                descriptionChangeResult.postValue(result)
            }
        }catch (e: Exception){
            descriptionChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val topBookChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun topBookChange(topBookData: TopBookData) = viewModelScope.launch(Dispatchers.IO) {
        topBookChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                topBookChangeResult.postValue(Resource.Loading())
                val result = topBookChangeUseCase.execute(topBookData)
                topBookChangeResult.postValue(result)
            }
        }catch (e: Exception){
            topBookChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun myBookList(type: String) = liveData {
        myBookListUseCase.execute(type).collect {
            emit(it)
        }
    }

    fun readingStatistics(userIdx: Int) = liveData {
        readingStatisticsUseCase.execute(userIdx).collect {
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