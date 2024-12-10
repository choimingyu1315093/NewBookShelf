package com.example.newbookshelf.data.model.detail.review

data class AddBookReviewData(
    val book_isbn: String,
    val comment_content: String,
    val comment_rate: Double
)