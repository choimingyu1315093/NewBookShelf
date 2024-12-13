package com.example.newbookshelf.domain.usecase.chat

import com.example.newbookshelf.data.model.chat.DeleteChatroomModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class DeleteChatroomUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, chatroomIdx: Int): Resource<DeleteChatroomModel> {
        return bookRepository.deleteChatroom(accessToken, chatroomIdx)
    }
}