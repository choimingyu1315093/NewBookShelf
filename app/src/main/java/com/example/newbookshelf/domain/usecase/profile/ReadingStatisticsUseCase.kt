package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.profile.ReadingStatisticsModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class ReadingStatisticsUseCase(private val bookRepository: BookRepository) {

    fun execute(userIdx: Int): Flow<Resource<ReadingStatisticsModel>> {
        return bookRepository.readingStatistics(userIdx)
    }
}