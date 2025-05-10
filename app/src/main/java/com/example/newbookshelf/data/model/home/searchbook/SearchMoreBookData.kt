package com.example.newbookshelf.data.model.home.searchbook

data class SearchMoreBookData(
    val popular_result_list: List<String>,
    val page: Int,
    val book_name: String,
    val book_author: String
)