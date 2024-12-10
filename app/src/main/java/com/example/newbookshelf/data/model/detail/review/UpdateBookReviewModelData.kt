package com.example.newbookshelf.data.model.detail.review

data class UpdateBookReviewModelData(
    val book_comment_idx: Int,
    val books: UpdateBookReviewModelBooks,
    val comment_content: String,
    val comment_rate: Int,
    val create_date: String,
    val delete_date: String?,
    val update_date: String,
    val users: UpdateBookReviewModelUsers
)