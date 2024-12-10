package com.example.newbookshelf.data.model.detail.detail

data class DetailBookComment(
    val book_comment_idx: Int,
    val comment_content: String,
    val comment_rate: Double,
    val create_date: String,
    val update_date: String,
    val users: DetailBookUser
)