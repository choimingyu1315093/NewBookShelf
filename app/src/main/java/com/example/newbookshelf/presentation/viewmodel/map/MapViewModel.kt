package com.example.newbookshelf.presentation.viewmodel.map

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.chat.ChatroomModel
import com.example.newbookshelf.data.model.chat.CreateChatroomData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.chat.CreateChatroomUseCase
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapViewModel(
    private val app: Application,
    private val wishBookHaveUserUseCase: WishBookHaveUserUseCase,
    private val createChatroomUseCase: CreateChatroomUseCase
): AndroidViewModel(app) {

    fun wishBookHaveUser() = liveData {
        wishBookHaveUserUseCase.execute().collect {
            emit(it)
        }
    }

    val createChatroomResult = MutableLiveData<Resource<ChatroomModel>>()
    fun createChatroom(accessToken: String, createChatroomData: CreateChatroomData) = viewModelScope.launch(Dispatchers.IO) {
        createChatroomResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                createChatroomResult.postValue(Resource.Loading())
                val result = createChatroomUseCase.execute("Bearer $accessToken", createChatroomData)
                createChatroomResult.postValue(result)
            }
        }catch (e: Exception){
            createChatroomResult.postValue(Resource.Error(e.message.toString()))
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