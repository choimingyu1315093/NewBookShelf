package com.example.newbookshelf.data.model.post.readingclass

data class BookComment(
    val book_comment_idx: Int,
    val comment_content: String,
    val comment_rate: Int,
    val create_date: String,
    val update_date: String,
    val users: UsersXX
)