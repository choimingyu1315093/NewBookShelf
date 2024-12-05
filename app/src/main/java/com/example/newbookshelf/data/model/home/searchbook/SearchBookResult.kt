package com.example.newbookshelf.data.model.home.searchbook

data class SearchBookResult(
    val book_author: String?,
    val book_average_rate: Double?,
    val book_content: String?,
    val book_image: String?,
    val book_isbn: String?,
    val book_name: String?,
    val book_publisher: String?,
    val book_translator: String?,
    val book_comments: ArrayList<SearchBookComment>?,
    val is_have_book: Boolean?,
    val read_type: String?
)
