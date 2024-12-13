package com.example.newbookshelf.data.model.chat

data class DeleteChatroomModelData(
    val chat_user_idx: Int,
    val is_exit: Int,
    val unread_count: Int
)