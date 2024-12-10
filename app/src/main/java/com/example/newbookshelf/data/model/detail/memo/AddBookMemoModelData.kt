package com.example.newbookshelf.data.model.detail.memo

data class AddBookMemoModelData(
    val books: AddBookMemoModelBooks,
    val create_date: String,
    val is_public: String,
    val memo_content: String,
    val memo_idx: Int,
    val users: AddBookMemoModelUsers
)