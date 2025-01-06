package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
import com.example.newbookshelf.data.model.home.notify.AlarmListModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class AlarmListUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(): Resource<AlarmListModel> {
        return bookRepository.alarmList()
    }
}