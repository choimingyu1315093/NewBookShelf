package com.example.newbookshelf.data.model.detail

data class AddMyBookData(
    val book_full_page: Int,
    val book_isbn: String,
    val is_have_book: String,
    val read_end_date: String?,
    val read_page: Int,
    val read_start_date: String?,
    val read_type: String
)