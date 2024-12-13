package com.example.newbookshelf.data.model.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatroomInfoModel(
    val chatroomIdx: Int,
    val image: String?,
    val name: String?
): Parcelable
