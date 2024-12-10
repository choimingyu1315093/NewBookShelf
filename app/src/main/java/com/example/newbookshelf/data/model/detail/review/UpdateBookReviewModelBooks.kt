package com.example.newbookshelf.data.model.detail.review

data class UpdateBookReviewModelBooks(
    val book_author: String,
    val book_average_rate: Int,
    val book_content: String,
    val book_full_page: Int,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
)