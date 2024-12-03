package com.example.newbookshelf.domain.usecase.find

import com.example.newbookshelf.data.model.find.FindModel
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class FindPwUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(findPwData: FindPwData): Resource<FindModel>{
        return bookRepository.findPw(findPwData)
    }
}