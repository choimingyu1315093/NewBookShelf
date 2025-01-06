package com.example.newbookshelf.presentation.viewmodel.post

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
import com.example.newbookshelf.data.model.post.AddScrapData
import com.example.newbookshelf.data.model.post.AddScrapModel
import com.example.newbookshelf.data.model.post.general.AddPostData
import com.example.newbookshelf.data.model.post.general.AddPostModel
import com.example.newbookshelf.data.model.post.general.PostCommentData
import com.example.newbookshelf.data.model.post.general.PostCommentModel
import com.example.newbookshelf.data.model.post.general.PostDetailModel
import com.example.newbookshelf.data.model.post.general.PostModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.post.AddPostUseCase
import com.example.newbookshelf.domain.usecase.post.AddScrapUseCase
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentUseCase
import com.example.newbookshelf.domain.usecase.post.PostDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostDetailUseCase
import com.example.newbookshelf.domain.usecase.post.PostListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(
    private val app: Application,
    private val kakaoSearchPlaceUseCase: KakaoSearchPlaceUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val postListUseCase: PostListUseCase,
    private val postDetailUseCase: PostDetailUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val postCommentDeleteUseCase: PostCommentDeleteUseCase,
    private val addScrapUseCase: AddScrapUseCase,
    private val postDeleteUseCase: PostDeleteUseCase
): AndroidViewModel(app) {

    fun searchPlace(accessToken: String, q: String) = liveData {
        kakaoSearchPlaceUseCase.execute("KakaoAK ${accessToken}", q).collect {
            emit(it)
        }
    }

    val addPostResult = MutableLiveData<Resource<AddPostModel>>()
    fun addPost(addPostData: AddPostData) = viewModelScope.launch(Dispatchers.IO) {
        addPostResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addPostResult.postValue(Resource.Loading())
                val result = addPostUseCase.execute(addPostData)
                addPostResult.postValue(result)
            }else {
                addPostResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            addPostResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val postListResult = MutableLiveData<Resource<PostModel>>()
    fun postList(userIdx: Int, limit: Int, currentPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        postListResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postListResult.postValue(Resource.Loading())
                val result = postListUseCase.execute(userIdx, limit, currentPage)
                postListResult.postValue(result)
            }else {
                postListResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            postListResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val postDetailResult = MutableLiveData<Resource<PostDetailModel>>()
    fun postDetail(postIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        postDetailResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postDetailResult.postValue(Resource.Loading())
                val result = postDetailUseCase.execute(postIdx)
                postDetailResult.postValue(result)
            }else {
                postDetailResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            postDetailResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val postCommentResult = MutableLiveData<Resource<PostCommentModel>>()
    fun postComment(postCommentData: PostCommentData) = viewModelScope.launch(Dispatchers.IO) {
        postCommentResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postCommentResult.postValue(Resource.Loading())
                val result = postCommentUseCase.execute(postCommentData)
                postCommentResult.postValue(result)
            }else {
                postCommentResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            postCommentResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val postCommentDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun postCommentDelete(postCommentIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        postCommentDeleteResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postCommentDeleteResult.postValue(Resource.Loading())
                val result = postCommentDeleteUseCase.execute(postCommentIdx)
                postCommentDeleteResult.postValue(result)
            }else {
                postCommentDeleteResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            postCommentDeleteResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val addScrapResult = MutableLiveData<Resource<AddScrapModel>>()
    fun addScrap(addScrapData: AddScrapData)= viewModelScope.launch(Dispatchers.IO) {
        addScrapResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addScrapResult.postValue(Resource.Loading())
                val result = addScrapUseCase.execute(addScrapData)
                addScrapResult.postValue(result)
            }else {
                addScrapResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            addScrapResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val postDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun postDelete(postIdx: Int)= viewModelScope.launch(Dispatchers.IO) {
        postDeleteResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postDeleteResult.postValue(Resource.Loading())
                val result = postDeleteUseCase.execute(postIdx)
                postDeleteResult.postValue(result)
            }else {
                postDeleteResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            postDeleteResult.postValue(Resource.Error(e.message.toString()))
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