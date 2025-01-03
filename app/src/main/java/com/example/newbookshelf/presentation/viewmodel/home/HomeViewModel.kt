package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.collection.emptyIntSet
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.home.AlarmAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmCountUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmListUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmOneDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.AlarmStatusUseCase
import com.example.newbookshelf.domain.usecase.home.AttentionBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.ChatStatusUseCase
import com.example.newbookshelf.domain.usecase.home.NewBestsellerUseCase
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import com.example.newbookshelf.domain.usecase.home.WeekBestsellerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val app: Application,
    private val weekBestsellerUseCase: WeekBestsellerUseCase,
    private val newBestsellerUseCase: NewBestsellerUseCase,
    private val attentionBestsellerUseCase: AttentionBestsellerUseCase,
    private val chatStatusUseCase: ChatStatusUseCase,
    private val alarmCountUseCase: AlarmCountUseCase,
    private val alarmStatusUseCase: AlarmStatusUseCase,
    private val alarmListUseCase: AlarmListUseCase,
    private val alarmAllDeleteUseCase: AlarmAllDeleteUseCase,
    private val alarmOneDeleteUseCase: AlarmOneDeleteUseCase,
    private val searchBookUseCase: SearchBookUseCase,
    private val searchedBookUseCase: SearchedBookUseCase,
    private val searchedBookInsertUseCase: SearchedBookInsertUseCase,
    private val searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
    private val searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
): AndroidViewModel(app) {

    fun weekBestseller(searchTarget: String, categoryId: Int) = liveData {
        weekBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun newBestseller(searchTarget: String, categoryId: Int) = liveData {
        newBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun attentionBestseller(searchTarget: String, categoryId: Int) = liveData {
        attentionBestsellerUseCase.execute(searchTarget, categoryId).collect {
            emit(it)
        }
    }

    fun chatStatus(accessToken: String) = liveData {
        chatStatusUseCase.execute(accessToken).collect {
            emit(it)
        }
    }

    fun alarmCount(accessToken: String) = liveData {
        alarmCountUseCase.execute("Bearer $accessToken").collect {
            emit(it)
        }
    }

    fun alarmStatus(accessToken: String) = liveData {
        alarmStatusUseCase.execute(accessToken).collect {
            emit(it)
        }
    }

    fun alarmList() = liveData {
        alarmListUseCase.execute().collect {
            emit(it)
        }
    }

    val alarmAllDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun alarmAllDelete() = viewModelScope.launch(Dispatchers.IO) {
        alarmAllDeleteResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                alarmAllDeleteResult.postValue(Resource.Loading())
                val result = alarmAllDeleteUseCase.execute()
                alarmAllDeleteResult.postValue(result)
            }else {
                alarmAllDeleteResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            alarmAllDeleteResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val alarmOneDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun alarmOneDelete(alarmIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        alarmOneDeleteResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                alarmOneDeleteResult.postValue(Resource.Loading())
                val result = alarmOneDeleteUseCase.execute(alarmIdx)
                alarmOneDeleteResult.postValue(result)
            }else {
                alarmOneDeleteResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            alarmOneDeleteResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val searchBook = MutableLiveData<Resource<SearchBookModel>>()
    fun getSearchBook(bookName: String) = viewModelScope.launch(Dispatchers.IO) {
        searchBook.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                searchBook.postValue(Resource.Loading())
                val result = searchBookUseCase.execute(bookName)
                searchBook.postValue(result)
            }else {
                searchBook.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            searchBook.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getSearchedBook() = liveData {
        searchedBookUseCase.execute().collect {
            emit(it)
        }
    }

    fun insertSearchedBook(searchedBook: SearchedBook) = viewModelScope.launch {
        searchedBookInsertUseCase.execute(searchedBook)
    }

    fun deleteSearchedBook(searchedBook: SearchedBook) = viewModelScope.launch {
        searchedBookDeleteUseCase.execute(searchedBook)
    }

    fun allDeleteSearchedBook() = viewModelScope.launch {
        searchedBookAllDeleteUseCase.execute()
    }

    private var _isDetail = MutableLiveData<Boolean>(false)
    val isDetail: LiveData<Boolean>
        get() = _isDetail

    fun setDetail(isDetail: Boolean) {
        _isDetail.value = isDetail
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