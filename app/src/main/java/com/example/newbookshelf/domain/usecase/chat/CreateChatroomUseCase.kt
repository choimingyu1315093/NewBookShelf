package com.example.newbookshelf.domain.usecase.chat

import com.example.newbookshelf.data.model.chat.ChatroomModel
import com.example.newbookshelf.data.model.chat.CreateChatroomData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class CreateChatroomUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, createChatroomData: CreateChatroomData): Resource<ChatroomModel> {
        return bookRepository.createChatroom(accessToken, createChatroomData)
    }
}