package com.example.newbookshelf.data.model.detail

data class DetailBookModelData(
    val book_isbn: String,
    val book_name: String,
    val book_content: String,
    val book_author: String,
    val book_publisher: String,
    val book_image: String,
    val book_full_page: Int,
    val is_have_book: String,
    val read_type: String,
    val read_start_date: String,
    val read_end_date: String,
    val read_page: Int,
    val edit_full_page: Boolean,
    val my_book_idx: Int,
    val book_translator: String,
    val book_average_rate: Double,
    val book_comments: ArrayList<DetailBookComment>
)