package com.example.newbookshelf.domain.usecase.find

import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class FindIdUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(findIdData: FindIdData): Resource<FindModel>{
        return bookRepository.findId(findIdData)
    }
}
