package com.example.newbookshelf.data.model.chat

data class ChatList(
    val books: ChatListBooks,
    val chat_room_idx: Int,
    val me_user: ChatListMeUser,
    val opponent_user: List<ChatListOpponentUser>,
    val recent_message: String,
    val recent_message_date: String?
)