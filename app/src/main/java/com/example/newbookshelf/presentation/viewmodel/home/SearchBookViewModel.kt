package com.example.newbookshelf.presentation.viewmodel.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.home.searchbook.SearchBookModel
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookData
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookModel
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.home.SearchBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchMoreBookUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookAllDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookDeleteUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookInsertUseCase
import com.example.newbookshelf.domain.usecase.home.SearchedBookUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchBookViewModel(
    private val app: Application,
    private val searchBookUseCase: SearchBookUseCase,
    private val searchMoreBookUseCase: SearchMoreBookUseCase,
    private val searchedBookUseCase: SearchedBookUseCase,
    private val searchedBookInsertUseCase: SearchedBookInsertUseCase,
    private val searchedBookDeleteUseCase: SearchedBookDeleteUseCase,
    private val searchedBookAllDeleteUseCase: SearchedBookAllDeleteUseCase
): AndroidViewModel(app) {

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

    val searchBook = MutableLiveData<Resource<SearchBookModel>>()
    val isEnd = MutableLiveData<Boolean>()
    fun getSearchBook(bookName: String) = viewModelScope.launch(Dispatchers.IO) {
        searchBook.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                searchBook.postValue(Resource.Loading())
                val result = searchBookUseCase.execute(bookName)
                searchBook.postValue(result)
                isEnd.postValue(result.data!!.data.is_end)
            }else {
                searchBook.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            searchBook.postValue(Resource.Error(e.message.toString()))
        }
    }

    val searchMoreBook = MutableLiveData<Resource<SearchMoreBookModel>>()
    fun getSearchMoreBook(searchMoreBookData: SearchMoreBookData) = viewModelScope.launch(
        Dispatchers.IO) {
        searchMoreBook.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                searchMoreBook.postValue(Resource.Loading())
                val result = searchMoreBookUseCase.execute(searchMoreBookData)
                searchMoreBook.postValue(result)
                isEnd.postValue(result.data!!.is_end)
            }else {
                searchMoreBook.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            searchMoreBook.postValue(Resource.Error(e.message.toString()))
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