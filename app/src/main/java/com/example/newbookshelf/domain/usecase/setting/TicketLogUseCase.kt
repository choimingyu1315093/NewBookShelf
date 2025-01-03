package com.example.newbookshelf.domain.usecase.setting

import com.example.newbookshelf.data.model.setting.TicketLogModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class TicketLogUseCase(private val bookRepository: BookRepository) {

    fun execute(): Flow<Resource<TicketLogModel>> {
        return bookRepository.ticketLog()
    }
}