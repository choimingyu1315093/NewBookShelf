package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class AlarmCountUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String): Flow<Resource<AlarmCountModel>>{
        return bookRepository.alarmCount(accessToken)
    }
}