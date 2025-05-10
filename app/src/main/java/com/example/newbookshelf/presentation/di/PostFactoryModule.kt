package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.data.model.post.general.PostDetailModelUsers
import com.example.newbookshelf.domain.usecase.chat.CreateChatroomUseCase
import com.example.newbookshelf.domain.usecase.map.WishBookHaveUserUseCase
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
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModelFactory
import com.example.newbookshelf.presentation.viewmodel.post.PostViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PostFactoryModule {

    @Singleton
    @Provides
    fun providePostViewModelFactory(
        app: Application,
        kakaoSearchPlaceUseCase: KakaoSearchPlaceUseCase,
        addPostUseCase: AddPostUseCase,
        postListUseCase: PostListUseCase,
        postDetailUseCase: PostDetailUseCase,
        postCommentUseCase: PostCommentUseCase,
        postCommentDeleteUseCase: PostCommentDeleteUseCase,
        addScrapUseCase: AddScrapUseCase,
        postDeleteUseCase: PostDeleteUseCase,
        addReadingClassUseCase: AddReadingClassUseCase,
        googleMapSearchLatLngUseCase: GoogleMapSearchLatLngUseCase,
        googleMapSearchPlaceUseCase: GoogleMapSearchPlaceUseCase,
        readingClassUseCase: ReadingClassUseCase,
        readingClassDetailUseCase: ReadingClassDetailUseCase,
        readingClassDeleteUseCase: ReadingClassDeleteUseCase,
        readingClassMemberListUseCase: ReadingClassMemberListUseCase,
        readingClassJoinUseCase: ReadingClassJoinUseCase
    ): PostViewModelFactory {
        return PostViewModelFactory(app, kakaoSearchPlaceUseCase, addPostUseCase, postListUseCase, postDetailUseCase, postCommentUseCase, postCommentDeleteUseCase, addScrapUseCase, postDeleteUseCase, addReadingClassUseCase, googleMapSearchLatLngUseCase, googleMapSearchPlaceUseCase, readingClassUseCase, readingClassDetailUseCase, readingClassDeleteUseCase, readingClassMemberListUseCase, readingClassJoinUseCase)
    }
}