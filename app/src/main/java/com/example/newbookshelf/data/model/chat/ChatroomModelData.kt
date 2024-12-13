package com.example.newbookshelf.data.model.chat

data class ChatroomModelData(
    val books: ChatroomModelBooks,
    val chat_room_idx: Int,
    val create_date: String,
    val recent_message: String,
    val recent_message_date: String?,
    val description: String?
)