package com.example.newbookshelf.data.model.profile

data class MemoModelData(
    val books: MemoBooks,
    val create_date: String,
    val is_public: String,
    val memo_content: String,
    val memo_idx: Int
)