package com.example.newbookshelf.data.model.home.searchbook

data class SearchMoreBookModel(
    val result: Boolean,
    val data: List<SearchBookResult>,
    val is_end: Boolean
)
