package com.example.newbookshelf.domain.usecase.detail

import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookData
import com.example.newbookshelf.data.model.detail.addmybook.AddMyBookModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddMyBookUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(addMyBookData: AddMyBookData): Resource<AddMyBookModel> {
        return bookRepository.addMyBook(addMyBookData)
    }
}