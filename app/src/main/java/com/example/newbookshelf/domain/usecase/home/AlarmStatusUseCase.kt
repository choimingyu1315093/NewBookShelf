package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.model.home.notify.AlarmCountModel
import com.example.newbookshelf.data.model.home.notify.StatusModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class AlarmStatusUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String): Flow<Resource<StatusModel>> {
        return bookRepository.alarmStatus(accessToken)
    }
}