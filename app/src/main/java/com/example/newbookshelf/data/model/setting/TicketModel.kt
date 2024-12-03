package com.example.newbookshelf.data.model.setting

data class TicketModel(
    val result: Boolean,
    val data: TicketModelData
)

data class TicketModelData(
    val ticket_count: Int
)