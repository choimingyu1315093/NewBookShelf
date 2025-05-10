package com.example.newbookshelf.presentation.viewmodel.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.data.model.post.general.PostDetailModelUsers
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
import com.example.newbookshelf.domain.usecase.post.ReadingClassJoinUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassMemberListUseCase
import com.example.newbookshelf.domain.usecase.post.ReadingClassUseCase
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel

class PostViewModelFactory(
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
    private val readingClassJoinUseCase: ReadingClassJoinUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(app, kakaoSearchPlaceUseCase, addPostUseCase, postListUseCase, postDetailUseCase, postCommentUseCase, postCommentDeleteUseCase, addScrapUseCase, postDeleteUseCase, addReadingClassUseCase, googleMapSearchLatLngUseCase, googleMapSearchPlaceUseCase, readingClassUseCase, readingClassDetailUseCase, readingClassDeleteUseCase, readingClassMemberListUseCase, readingClassJoinUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}