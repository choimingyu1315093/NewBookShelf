package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.AddBookReviewModel
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewData
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class UpdateBookReviewUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(bookCommentIdx: Int, updateBookReviewData: UpdateBookReviewData): Resource<UpdateBookReviewModel> {
        return bookRepository.updateBookReview(bookCommentIdx, updateBookReviewData)
    }
}