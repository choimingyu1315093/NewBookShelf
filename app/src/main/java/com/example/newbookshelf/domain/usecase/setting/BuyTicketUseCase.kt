package com.example.newbookshelf.domain.usecase.setting

import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.setting.TicketModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class BuyTicketUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(accessToken: String, ticketData: TicketData): Resource<TicketModel> {
        return bookRepository.buyTicket(accessToken, ticketData)
    }
}