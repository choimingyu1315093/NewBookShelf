package com.example.newbookshelf.presentation.viewmodel.setting

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
import com.example.newbookshelf.data.model.setting.PasswordChangeData
import com.example.newbookshelf.data.model.setting.UpdateUserSettingData
import com.example.newbookshelf.data.model.setting.UserSettingModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.setting.PasswordChangeUseCase
import com.example.newbookshelf.domain.usecase.setting.TicketLogUseCase
import com.example.newbookshelf.domain.usecase.setting.UpdateUserSettingUseCase
import com.example.newbookshelf.domain.usecase.setting.UserDeleteUseCase
import com.example.newbookshelf.domain.usecase.setting.UserSettingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val app: Application,
    private val userSettingUseCase: UserSettingUseCase,
    private val updateUserSettingUseCase: UpdateUserSettingUseCase,
    private val ticketLogUseCase: TicketLogUseCase,
    private val passwordChangeUseCase: PasswordChangeUseCase,
    private val userDeleteUseCase: UserDeleteUseCase
): AndroidViewModel(app) {

    private var _userSettingResult = MutableStateFlow<Resource<UserSettingModel>>(Resource.Idle())
    val userSettingResult: StateFlow<Resource<UserSettingModel>>
        get() = _userSettingResult

    fun userSetting() = viewModelScope.launch(Dispatchers.IO){
        try {
            if(isNetworkAvailable(app)){
                _userSettingResult.emit(Resource.Loading())
                val result = userSettingUseCase.execute()
                _userSettingResult.emit(result)
            }
        }catch (e: Exception){
            _userSettingResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val updateUserSettingResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun updateUserSetting(updateUserSettingData: UpdateUserSettingData) = viewModelScope.launch(Dispatchers.IO) {
        updateUserSettingResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                updateUserSettingResult.postValue(Resource.Loading())
                val result = updateUserSettingUseCase.execute(updateUserSettingData)
                updateUserSettingResult.postValue(result)
            }
        }catch (e: Exception){
            updateUserSettingResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun ticketLog() = liveData {
        ticketLogUseCase.execute().collect {
            emit(it)
        }
    }

    val passwordChangeResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun passwordChange(passwordChangeData: PasswordChangeData) = viewModelScope.launch(Dispatchers.IO) {
        passwordChangeResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                passwordChangeResult.postValue(Resource.Loading())
                val result = passwordChangeUseCase.execute(passwordChangeData)
                passwordChangeResult.postValue(result)
            }
        }catch (e: Exception){
            passwordChangeResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val userDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun userDelete() = viewModelScope.launch(Dispatchers.IO) {
        userDeleteResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                userDeleteResult.postValue(Resource.Loading())
                val result = userDeleteUseCase.execute()
                userDeleteResult.postValue(result)
            }
        }catch (e: Exception){
            userDeleteResult.postValue(Resource.Error(e.message.toString()))
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