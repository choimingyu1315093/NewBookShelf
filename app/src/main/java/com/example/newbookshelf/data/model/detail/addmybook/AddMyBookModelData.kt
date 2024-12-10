package com.example.newbookshelf.data.model.detail.addmybook

import com.example.newbookshelf.data.model.detail.detail.DetailBookModelData

data class AddMyBookModelData(
    val book_full_page: Int,
    val book_isbn: String,
    val books: DetailBookModelData,
    val is_have_book: String,
    val my_book_idx: String,
    val read_end_date: String?,
    val read_page: Int,
    val read_start_date: String?,
    val read_type: String,
    val users: AddMyBookUser
)
