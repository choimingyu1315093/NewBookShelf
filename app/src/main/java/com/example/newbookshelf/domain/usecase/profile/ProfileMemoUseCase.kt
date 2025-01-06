package com.example.newbookshelf.domain.usecase.profile

import com.example.newbookshelf.data.model.profile.MemoModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class ProfileMemoUseCase(private val bookRepository: BookRepository) {

    fun execute(): Flow<Resource<MemoModel>> {
        return bookRepository.profileMemo()
    }
}