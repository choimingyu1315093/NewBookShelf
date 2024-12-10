package com.example.newbookshelf.data.model.detail.memo

data class AddBookMemoModelUsers(
    val create_date: String,
    val current_latitude: String,
    val current_longitude: String,
    val delete_date: String?,
    val fcm_token: String,
    val login_type: String,
    val setting_chat_alarm: Int,
    val setting_chat_receive: Int,
    val setting_marketing_alarm: Int,
    val setting_wish_book_alarm: Int,
    val ticket_count: Int,
    val user_description: String,
    val user_email: String,
    val user_id: String,
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
)