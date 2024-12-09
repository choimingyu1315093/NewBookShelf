package com.example.newbookshelf.data.model.home.searchbook

data class SearchBookModelData(
    val general_result: List<SearchBookResult>?,
    val is_end: Boolean,
    val popular_result: List<SearchBookResult>?
)