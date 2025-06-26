package com.example.newbookshelf.presentation.viewmodel.post

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassData
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassDetailModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassFinishData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinData
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassJoinModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassMembersModel
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.usecase.post.AddPostUseCase
import com.example.newbookshelf.domain.usecase.post.AddReadingClassUseCase
import com.example.newbookshelf.domain.usecase.post.AddScrapUseCase
import com.example.newbookshelf.domain.usecase.post.GoogleMapSearchLatLngUseCase
import com.example.newbookshelf.domain.usecase.post.GoogleMapSearchPlaceUseCase
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentUseCase
import com.example.newbookshelf.domain.usecase.post.PostDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostDetailUseCase
import com.example.newbookshelf.domain.usecase.post.PostListUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassDetailUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassFinishUserCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassJoinUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassMemberListUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val postDeleteUseCase: PostDeleteUseCase,
    private val addReadingClassUseCase: AddReadingClassUseCase,
    private val googleMapSearchLatLngUseCase: GoogleMapSearchLatLngUseCase,
    private val googleMapSearchPlaceUseCase: GoogleMapSearchPlaceUseCase,
    private val readingClassUseCase: ReadingClassUseCase,
    private val readingClassDetailUseCase: ReadingClassDetailUseCase,
    private val readingClassDeleteUseCase: ReadingClassDeleteUseCase,
    private val readingClassMemberListUseCase: ReadingClassMemberListUseCase,
    private val readingClassJoinUseCase: ReadingClassJoinUseCase,
    private val readingClassFinishUseCase: ReadingClassFinishUserCase
): AndroidViewModel(app) {

    private var _searchPlacesResult = MutableSharedFlow<Resource<KakaoMapModel>>()
    val searchPlacesResult = _searchPlacesResult.asSharedFlow()

    fun searchPlaces(kakaoKey: String, place: String) = viewModelScope.launch(Dispatchers.IO) {
        if(isNetworkAvailable(app)){
            val result = kakaoSearchPlaceUseCase.execute("KakaoAK $kakaoKey", place)

            when(result){
                is Resource.Success -> {
                    _searchPlacesResult.emit(result)
                }
                is Resource.Loading -> Unit
                is Resource.Error -> Unit
                else -> Unit
            }
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
    fun postList(limit: Int, currentPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        postListResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                postListResult.postValue(Resource.Loading())
                val result = postListUseCase.execute(limit, currentPage)
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

    val addScrapResult = MutableSharedFlow<Resource<AddScrapModel>>()
    fun addScrap(addScrapData: AddScrapData) = viewModelScope.launch(Dispatchers.IO) {
        addScrapResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addScrapResult.emit(Resource.Loading())
                val result = addScrapUseCase.execute(addScrapData)
                addScrapResult.emit(result)
            }else {
                addScrapResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            addScrapResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val postDeleteResult = MutableLiveData<Resource<OnlyResultModel>>()
    fun postDelete(postIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
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

    val addReadingClassResult = MutableLiveData<Resource<AddReadingClassModel>>()
    fun addReadingClass(addReadingClassData: AddReadingClassData) = viewModelScope.launch(Dispatchers.IO) {
        addReadingClassResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                addReadingClassResult.postValue(Resource.Loading())
                val result = addReadingClassUseCase.execute(addReadingClassData)
                addReadingClassResult.postValue(result)
            }else {
                addReadingClassResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            addReadingClassResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val readingClassListResult = MutableLiveData<Resource<ReadingClassModel>>()
    fun readingClassList(searchWord: String, filterType: String, limit: Int, currentPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        readingClassListResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassListResult.postValue(Resource.Loading())
                val result = readingClassUseCase.execute(searchWord, filterType, limit, currentPage)
                readingClassListResult.postValue(result)
            }else {
                readingClassListResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassListResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val readingClassDetailResult = MutableSharedFlow<Resource<ReadingClassDetailModel>>()
    fun readingClassDetail(readingClassIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        readingClassDetailResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassDetailResult.emit(Resource.Loading())
                val result = readingClassDetailUseCase.execute(readingClassIdx)
                readingClassDetailResult.emit(result)
            }else {
                readingClassDetailResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassDetailResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val readingClassDeleteResult = MutableSharedFlow<Resource<OnlyResultModel>>()
    fun readingClassDelete(readingClassIdx: Int) = viewModelScope.launch(Dispatchers.IO) {
        readingClassDeleteResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassDeleteResult.emit(Resource.Loading())
                val result = readingClassDeleteUseCase.execute(readingClassIdx)
                Log.d("TAG", "readingClassDelete: $result ")
                readingClassDeleteResult.emit(result)
            }else {
                readingClassDeleteResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassDeleteResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val readingClassMemberListResult = MutableLiveData<Resource<ReadingClassMembersModel>>()
    fun readingClassMemberList(readingClassIdx: Int, limit: Int, currentPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        readingClassMemberListResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassMemberListResult.postValue(Resource.Loading())
                val result = readingClassMemberListUseCase.execute(readingClassIdx, limit, currentPage)
                readingClassMemberListResult.postValue(result)
            }else {
                readingClassMemberListResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassMemberListResult.postValue(Resource.Error(e.message.toString()))
        }
    }

    val readingClassJoinResult = MutableSharedFlow<Resource<ReadingClassJoinModel>>()
    fun readingClassJoin(readingClassJoinData: ReadingClassJoinData) = viewModelScope.launch(Dispatchers.IO) {
        readingClassJoinResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassJoinResult.emit(Resource.Loading())
                val result = readingClassJoinUseCase.execute(readingClassJoinData)
                readingClassJoinResult.emit(result)
            }else {
                readingClassJoinResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassJoinResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val readingClassFinishResult = MutableSharedFlow<Resource<OnlyResultModel>>()
    fun readingClassFinish(readingClassFinishData: ReadingClassFinishData) = viewModelScope.launch(Dispatchers.IO) {
        readingClassFinishResult.emit(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                readingClassFinishResult.emit(Resource.Loading())
                val result = readingClassFinishUseCase.execute(readingClassFinishData)
                readingClassFinishResult.emit(result)
            }else {
                readingClassFinishResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            readingClassFinishResult.emit(Resource.Error(e.message.toString()))
        }
    }

    private var _googleMapLatLngResult = MutableSharedFlow<Resource<GeocodingModel>>()
    val googleMapLatLngResult = _googleMapLatLngResult.asSharedFlow()

    fun getCoordinatesForAddress(address: String, apiKey: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if(isNetworkAvailable(app)){
                val result = googleMapSearchLatLngUseCase.execute(address, apiKey)

                when(result){
                    is Resource.Success -> {
                        _googleMapLatLngResult.emit(result)
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    else -> Unit
                }
            }else {
                _googleMapLatLngResult.emit(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            _googleMapLatLngResult.emit(Resource.Error(e.message.toString()))
        }
    }

    val googleMapPlaceResult = MutableLiveData<Resource<GeocodingModel>>()
    fun getAddressForCoordinates(latLng: String, apiKey: String) = viewModelScope.launch(Dispatchers.IO) {
        googleMapPlaceResult.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                googleMapPlaceResult.postValue(Resource.Loading())
                val result = googleMapSearchPlaceUseCase.execute(latLng, apiKey)
                googleMapPlaceResult.postValue(result)
            }else {
                googleMapPlaceResult.postValue(Resource.Error("인터넷이 연결되지 않았습니다."))
            }
        }catch (e: Exception){
            googleMapPlaceResult.postValue(Resource.Error(e.message.toString()))
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