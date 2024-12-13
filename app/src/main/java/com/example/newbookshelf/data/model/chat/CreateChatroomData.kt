package com.example.newbookshelf.data.model.chat

data class CreateChatroomData(
    val book_isbn: String,
    val opponent_user_idx: Int
)