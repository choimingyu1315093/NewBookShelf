package com.example.newbookshelf.presentation.viewmodel.chat

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.chat.DeleteChatroomModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.chat.DeleteChatroomUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    private val app: Application,
    private val deleteChatroomUseCase: DeleteChatroomUseCase
): AndroidViewModel(app) {

    val deleteChatroomResult = MutableLiveData<Resource<DeleteChatroomModel>>()
    fun deleteChatroom(accessToken: String, chatroomIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteChatroomResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                deleteChatroomResult.postValue(Resource.Loading())
                val result = deleteChatroomUseCase.execute("Bearer $accessToken", chatroomIdx)
                deleteChatroomResult.postValue(result)
            }
        }catch (e: Exception){
            deleteChatroomResult.postValue(Resource.Error(e.message.toString()))
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