package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.common.OnlyResultModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AlarmAllDeleteUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(): Resource<OnlyResultModel> {
        return bookRepository.alarmAllDelete()
    }
}