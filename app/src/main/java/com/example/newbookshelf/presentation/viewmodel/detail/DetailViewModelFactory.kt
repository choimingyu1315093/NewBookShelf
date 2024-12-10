package com.example.newbookshelf.presentation.viewmodel.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.detail.AddBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.AddBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.AddMyBookUseCase
import com.example.newbookshelf.domain.usecase.detail.BookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.DeleteBookReviewUseCase
import com.example.newbookshelf.domain.usecase.detail.DetailBookUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookMemoUseCase
import com.example.newbookshelf.domain.usecase.detail.UpdateBookReviewUseCase
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class DetailViewModelFactory(
    private val app: Application,
    private val detailBookUseCase: DetailBookUseCase,
    private val addMyBookUseCase: AddMyBookUseCase,
    private val addBookReviewUseCase: AddBookReviewUseCase,
    private val updateBookReviewUseCase: UpdateBookReviewUseCase,
    private val deleteBookReviewUseCase: DeleteBookReviewUseCase,
    private val bookMemoUseCase: BookMemoUseCase,
    private val addBookMemoUseCase: AddBookMemoUseCase,
    private val updateBookMemoUseCase: UpdateBookMemoUseCase,
    private val deleteBookMemoUseCase: DeleteBookMemoUseCase,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(app, detailBookUseCase, addMyBookUseCase, addBookReviewUseCase, updateBookReviewUseCase, deleteBookReviewUseCase, bookMemoUseCase, addBookMemoUseCase, updateBookMemoUseCase, deleteBookMemoUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}