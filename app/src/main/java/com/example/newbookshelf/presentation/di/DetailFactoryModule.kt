package com.example.newbookshelf.presentation.di

import android.app.Application
import com.example.newbookshelf.domain.usecase.detail.AddBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.AddBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.BookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookReviewUseCase
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DetailFactoryModule {

    @Singleton
    @Provides
    fun provideDetailViewModelFactory(
        application: Application,
        detailBookUseCase: DetailBookUseCase,
        addMyBookUseCase: AddMyBookUseCase,
        addBookReviewUseCase: AddBookReviewUseCase,
        updateBookReviewUseCase: UpdateBookReviewUseCase,
        deleteBookReviewUseCase: DeleteBookReviewUseCase,
        bookMemoUseCase: BookMemoUseCase,
        addBookMemoUseCase: AddBookMemoUseCase,
        updateBookMemoUseCase: UpdateBookMemoUseCase,
        deleteBookMemoUseCase: DeleteBookMemoUseCase
    ): DetailViewModelFactory {
        return DetailViewModelFactory(application, detailBookUseCase, addMyBookUseCase, addBookReviewUseCase, updateBookReviewUseCase, deleteBookReviewUseCase, bookMemoUseCase, addBookMemoUseCase, updateBookMemoUseCase, deleteBookMemoUseCase)
    }
}