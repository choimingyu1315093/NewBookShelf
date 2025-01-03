package com.example.newbookshelf.data.model.setting

data class UserSettingModelData(
    val setting_chat_alarm: Int,
    val setting_chat_receive: Int,
    val setting_marketing_alarm: Int,
    val setting_wish_book_alarm: Int,
    val setting_recommend_club: Int,
    val ticket_count: Int,
    val user_idx: Int,
    val user_name: String
)