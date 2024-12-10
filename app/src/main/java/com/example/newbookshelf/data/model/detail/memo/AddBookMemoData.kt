package com.example.newbookshelf.data.model.detail.memo

data class AddBookMemoData(
    val book_isbn: String,
    val is_public: String,
    val memo_content: String
)