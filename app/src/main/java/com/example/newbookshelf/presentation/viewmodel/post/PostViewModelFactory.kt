package com.example.newbookshelf.presentation.viewmodel.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.data.model.post.general.PostDetailModelUsers
import com.example.newbookshelf.domain.usecase.post.AddPostUseCase
import com.example.newbookshelf.domain.usecase.post.AddScrapUseCase
import com.example.newbookshelf.domain.usecase.post.KakaoSearchPlaceUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostCommentUseCase
import com.example.newbookshelf.domain.usecase.post.PostDeleteUseCase
import com.example.newbookshelf.domain.usecase.post.PostDetailUseCase
import com.example.newbookshelf.domain.usecase.post.PostListUseCase
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
    private val postDeleteUseCase: PostDeleteUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(app, kakaoSearchPlaceUseCase, addPostUseCase, postListUseCase, postDetailUseCase, postCommentUseCase, postCommentDeleteUseCase, addScrapUseCase, postDeleteUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}