package com.example.newbookshelf.presentation.viewmodel.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.login.SnsLoginData
import com.example.newbookshelf.data.model.login.UpdateLocationData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.login.IdLoginUseCase
import com.example.newbookshelf.domain.usecase.login.SnsLoginUseCase
import com.example.newbookshelf.domain.usecase.login.UpdateLocationUseCase
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val app: Application,
    private val loginUseCase: IdLoginUseCase,
    private val snsLoginUseCase: SnsLoginUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase
): AndroidViewModel(app) {

    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()

    private var _loginResult = MutableStateFlow<Resource<LoginModel>>(Resource.Idle())
    val loginResult: StateFlow<Resource<LoginModel>>
        get() = _loginResult

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object NavigateToSuccessScreen: UiEvent()
    }

    fun login(loginData: LoginData) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(isNetworkAvailable(app)){
                _loginResult.value = Resource.Loading()
                val result = loginUseCase.execute(loginData)
                _loginResult.value = result

                when(result){
                    is Resource.Success -> {
                        _eventFlow.emit(UiEvent.NavigateToSuccessScreen)
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowToast(result.message ?: "알 수 없는 오류"))
                    }
                    else -> Unit
                }
                BookShelfApp.prefs.setLoginType("loginType", "general")
                BookShelfApp.prefs.setLoginId("id", loginData.userId)
                BookShelfApp.prefs.setLoginPw("password", loginData.userPassword)
            }
        }catch (e: Exception){
            _loginResult.value = Resource.Error(e.message.toString())
            _eventFlow.emit(UiEvent.ShowToast(e.message.toString()))
        }
    }

    fun snsLogin(snsLoginData: SnsLoginData) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(isNetworkAvailable(app)){
                _loginResult.value = Resource.Loading()
                val result = snsLoginUseCase.execute(snsLoginData)
                _loginResult.value = result

                when(result){
                    is Resource.Success -> {
                        _eventFlow.emit(UiEvent.NavigateToSuccessScreen)
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowToast(result.message ?: "알 수 없는 오류"))
                    }
                    else -> Unit
                }
            }
        }catch (e: Exception){
            _loginResult.value = Resource.Error(e.message.toString())
            _eventFlow.emit(UiEvent.ShowToast(e.message.toString()))
        }
    }

    val updateLocationResult = MutableSharedFlow<Resource<OnlyResultModel>>()
    fun updateLocation(updateLocationData: UpdateLocationData) = viewModelScope.launch(Dispatchers.IO) {
        updateLocationResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                updateLocationResult.emit(Resource.Loading())
                val result = updateLocationUseCase.execute(updateLocationData)
                updateLocationResult.emit(result)
            }
        }catch (e: Exception){
            updateLocationResult.emit(Resource.Error(e.message.toString()))
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