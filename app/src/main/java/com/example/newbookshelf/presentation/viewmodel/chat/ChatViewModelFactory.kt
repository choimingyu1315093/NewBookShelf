package com.example.newbookshelf.presentation.viewmodel.chat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newbookshelf.domain.usecase.chat.DeleteChatroomUseCase
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel

class ChatViewModelFactory(
    private val app: Application,
    private val deleteChatroomUseCase: DeleteChatroomUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)){
            return ChatViewModel(app, deleteChatroomUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}