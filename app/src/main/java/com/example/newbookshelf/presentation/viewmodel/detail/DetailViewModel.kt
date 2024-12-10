package com.example.newbookshelf.presentation.viewmodel.detail

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookData
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookModel
import com.example.newbookshelf.data.model.detail.detail.DetailBookModel
import com.example.newbookshelf.data.model.detail.memo.GetBookMemoModel
import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.AddBookReviewModel
import com.example.newbookshelf.data.model.detail.review.DeleteBookReviewModel
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewData
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.detail.AddBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.BookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookReviewUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val app: Application,
    private val detailBookUseCase: DetailBookUseCase,
    private val addMyBookUseCase: AddMyBookUseCase,
    private val bookMemoUseCase: BookMemoUseCase,
    private val addBookReviewUseCase: AddBookReviewUseCase,
    private val updateBookReviewUseCase: UpdateBookReviewUseCase,
    private val deleteBookReviewUseCase: DeleteBookReviewUseCase
): AndroidViewModel(app) {

    val detailBookResult = MutableLiveData<Resource<DetailBookModel>>()
    fun detailBook(accessToken: String, isbn: String) = viewModelScope.launch(Dispatchers.IO) {
        detailBookResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                detailBookResult.postValue(Resource.Loading())
                val result = detailBookUseCase.execute("Bearer $accessToken", isbn)
                detailBookResult.postValue(result)
            }
        }catch (e: Exception){
            detailBookResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val addMyBookResult = MutableLiveData<Resource<AddMyBookModel>>()
    fun addMyBook(accessToken: String, addMyBookData: AddMyBookData) = viewModelScope.launch(Dispatchers.IO) {
        addMyBookResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addMyBookResult.postValue(Resource.Loading())
                val result = addMyBookUseCase.execute("Bearer $accessToken", addMyBookData)
                addMyBookResult.postValue(result)
            }
        }catch (e: Exception){
            addMyBookResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val getBookMemoResult = MutableLiveData<Resource<GetBookMemoModel>>()
    fun getBookMemo(accessToken: String, isbn: String, getType: String) = viewModelScope.launch(Dispatchers.IO) {
        getBookMemoResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                getBookMemoResult.postValue(Resource.Loading())
                val result = bookMemoUseCase.execute("Bearer $accessToken", isbn, getType)
                getBookMemoResult.postValue(result)
            }
        }catch (e: Exception){
            getBookMemoResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val addBookReviewResult = MutableLiveData<Resource<AddBookReviewModel>>()
    fun addBookReview(accessToken: String, addBookReviewData: AddBookReviewData) = viewModelScope.launch(Dispatchers.IO) {
        addBookReviewResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addBookReviewResult.postValue(Resource.Loading())
                val result = addBookReviewUseCase.execute("Bearer $accessToken", addBookReviewData)
                addBookReviewResult.postValue(result)
            }
        }catch (e: Exception){
            addBookReviewResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val updateBookReviewResult = MutableLiveData<Resource<UpdateBookReviewModel>>()
    fun updateBookReview(accessToken: String, bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData) = viewModelScope.launch(Dispatchers.IO) {
        updateBookReviewResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                updateBookReviewResult.postValue(Resource.Loading())
                val result = updateBookReviewUseCase.execute("Bearer $accessToken", bookCommentIdx, updateBookReviewData)
                updateBookReviewResult.postValue(result)
            }
        }catch (e: Exception){
            updateBookReviewResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val deleteBookReviewResult = MutableLiveData<Resource<DeleteBookReviewModel>>()
    fun deleteBookReview(accessToken: String, bookCommentIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteBookReviewResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                deleteBookReviewResult.postValue(Resource.Loading())
                val result = deleteBookReviewUseCase.execute("Bearer $accessToken", bookCommentIdx)
                deleteBookReviewResult.postValue(result)
            }
        }catch (e: Exception){
            deleteBookReviewResult.postValue(Resource.Error(e.message.toString()))
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