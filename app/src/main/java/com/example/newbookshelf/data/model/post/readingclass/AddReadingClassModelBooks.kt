package com.example.newbookshelf.data.model.post.readingclass

data class AddReadingClassModelBooks(
    val book_author: String,
    val book_average_rate: Double,
    val book_comments: List<BookComment>,
    val book_content: String,
    val book_full_page: Int,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val edit_full_page: Boolean,
    val is_have_book: Boolean,
    val read_end_date: String,
    val read_page: Int,
    val read_start_date: String,
    val read_type: String
)