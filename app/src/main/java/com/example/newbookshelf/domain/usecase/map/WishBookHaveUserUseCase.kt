package com.example.newbookshelf.domain.usecase.map

import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class WishBookHaveUserUseCase(private val bookRepository: BookRepository) {

    fun execute(accessToken: String): Flow<Resource<WishBookHaveUserModel>> {
        return bookRepository.wishBookHaveUser(accessToken)
    }
}