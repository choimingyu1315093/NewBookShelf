package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.AddBookReviewModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddBookReviewUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, addBookReviewData: AddBookReviewData): Resource<AddBookReviewModel>{
        return bookRepository.addBookReview(accessToken, addBookReviewData)
    }
}