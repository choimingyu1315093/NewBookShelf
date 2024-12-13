package com.example.newbookshelf.data.model.chat

data class ChatMessageModelData(
    val chat_message_idx: Int,
    val create_date: String,
    val message_content: String,
    val users: Users
)
