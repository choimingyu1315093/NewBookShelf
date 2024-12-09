package com.example.newbookshelf.data.model.home.searchbook

data class SearchBookComment(
    val book_comment_idx: Int?,
    val comment_rate: Int?,
    val comment_content: String?,
    val create_date: String?,
    val update_date: String?,
    val users: SearchBookUser
)