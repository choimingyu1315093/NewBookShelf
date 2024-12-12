package com.example.newbookshelf.data.model.map

data class WishBookHaveUserModelData(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val current_latitude: String,
    val current_longitude: String,
    val distance: Int,
    val my_book_idx: Int,
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
)