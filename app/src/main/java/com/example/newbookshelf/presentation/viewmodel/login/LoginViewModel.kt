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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val app: Application,
    private val loginUseCase: IdLoginUseCase,
    private val snsLoginUseCase: SnsLoginUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase
): AndroidViewModel(app) {

    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()

    val loginResult = MutableLiveData<Resource<LoginModel>>()

    fun login(loginData: LoginData) = viewModelScope.launch(Dispatchers.IO) {
        loginResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                loginResult.postValue(Resource.Loading())
                val result = loginUseCase.execute(loginData)
                loginResult.postValue(result)
                BookShelfApp.prefs.setLoginType("loginType", "general")
                BookShelfApp.prefs.setLoginId("id", loginData.userId)
                BookShelfApp.prefs.setLoginPw("password", loginData.userPassword)
            }
        }catch (e: Exception){
            loginResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun snsLogin(snsLoginData: SnsLoginData) = viewModelScope.launch(Dispatchers.IO) {
        loginResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                loginResult.postValue(Resource.Loading())
                val result = snsLoginUseCase.execute(snsLoginData)
                loginResult.postValue(result)
            }
        }catch (e: Exception){
            loginResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val updateLocationResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun updateLocation(accessToken: String, updateLocationData: UpdateLocationData) = viewModelScope.launch(Dispatchers.IO) {
        updateLocationResult.postValue(Resource.Loading())
        val result = updateLocationUseCase.execute("Bearer $accessToken", updateLocationData)
        updateLocationResult.postValue(result)
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