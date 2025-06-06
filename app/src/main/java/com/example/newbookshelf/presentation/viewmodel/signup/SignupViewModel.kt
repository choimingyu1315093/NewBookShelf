package com.example.newbookshelf.presentation.viewmodel.signup

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.model.signup.CheckModel
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.model.signup.SignupModel
import com.example.newbookshelf.data.model.signup.SnsSignupData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.setting.BuyTicketUseCase
import com.example.newbookshelf.domain.usecase.signup.EmailCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.IdCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.NicknameCheckUseCase
import com.example.newbookshelf.domain.usecase.signup.SignupUseCase
import com.example.newbookshelf.domain.usecase.signup.SnsSignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val app: Application,
    private val signupUseCase: SignupUseCase,
    private val snsSignupUseCase: SnsSignupUseCase,
    private val idCheckUseCase: IdCheckUseCase,
    private val emailCheckUseCase: EmailCheckUseCase,
    private val nicknameCheckUseCase: NicknameCheckUseCase,
): AndroidViewModel(app) {

    data class AgreeState(
        val isCb1Checked: Boolean = false,
        val isCb2Checked: Boolean = false,
        val isCb3Checked: Boolean = false,
        val isCb4Checked: Boolean = false,
    ) {
        val isAllChecked: Boolean
            get() = isCb1Checked && isCb2Checked && isCb3Checked && isCb4Checked

        val isNextEnabled: Boolean
            get() = isCb1Checked && isCb2Checked
    }

    private val _agreeState = MutableStateFlow(AgreeState())
    val agreeState: StateFlow<AgreeState> = _agreeState

    fun setCheckBox(index: Int, checked: Boolean) {
        _agreeState.update { state ->
            when (index) {
                1 -> state.copy(isCb1Checked = checked)
                2 -> state.copy(isCb2Checked = checked)
                3 -> state.copy(isCb3Checked = checked)
                4 -> state.copy(isCb4Checked = checked)
                else -> state
            }
        }
    }

    fun setAllChecked(checked: Boolean) {
        _agreeState.value = AgreeState(
            isCb1Checked = checked,
            isCb2Checked = checked,
            isCb3Checked = checked,
            isCb4Checked = checked,
        )
    }

    private var _signupResult = MutableStateFlow<Resource<SignupModel>>(Resource.Idle())
    val signupResult: StateFlow<Resource<SignupModel>>
        get() = _signupResult

    // 일회성 이벤트를 위한 SharedFlow
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // 이벤트 정의
    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object NavigateToSuccessScreen: UiEvent()
    }

    fun signup(signupData: SignupData) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(isNetworkAvailable(app)){
                _signupResult.value = Resource.Loading()
                val result = signupUseCase.execute(signupData)
                _signupResult.value = result

                when (result) {
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
            _signupResult.value = Resource.Error(e.message.toString())
            _eventFlow.emit(UiEvent.ShowToast(e.message.toString()))
        }
    }

    fun snsSignup(snsSignupData: SnsSignupData) = viewModelScope.launch(Dispatchers.IO) {
        _signupResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                _signupResult.emit(Resource.Loading())
                val result = snsSignupUseCase.execute(snsSignupData)
                _signupResult.emit(result)
            }
        }catch (e: Exception){
            _signupResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val idCheckResult = MutableStateFlow<Resource<CheckModel>>(Resource.Loading())
    fun idCheck(id: String) = viewModelScope.launch(Dispatchers.IO) {
        idCheckResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                idCheckResult.emit(Resource.Loading())
                val result = idCheckUseCase.execute(id)
                idCheckResult.emit(result)
            }
        }catch (e: Exception){
            idCheckResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val emailCheckResult = MutableStateFlow<Resource<CheckModel>>(Resource.Loading())
    fun emailCheck(emailCheckData: EmailCheckData) = viewModelScope.launch(Dispatchers.IO) {
        emailCheckResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                emailCheckResult.emit(Resource.Loading())
                val result = emailCheckUseCase.execute(emailCheckData)
                emailCheckResult.emit(result)
            }
        }catch (e: Exception){
            emailCheckResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val nicknameCheckResult = MutableStateFlow<Resource<CheckModel>>(Resource.Loading())
    fun nicknameCheck(nickname: String) = viewModelScope.launch(Dispatchers.IO) {
        nicknameCheckResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                nicknameCheckResult.emit(Resource.Loading())
                val result = nicknameCheckUseCase.execute(nickname)
                nicknameCheckResult.emit(result)
            }
        }catch (e: Exception){
            nicknameCheckResult.emit(Resource.Error(e.message.toString()))
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