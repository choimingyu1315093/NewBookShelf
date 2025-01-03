package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.review.DeleteBookReviewModel
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class DeleteBookReviewUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(bookCommentIdx: Int): Resource<DeleteBookReviewModel>{
        return bookRepository.deleteBookReview(bookCommentIdx)
    }
}