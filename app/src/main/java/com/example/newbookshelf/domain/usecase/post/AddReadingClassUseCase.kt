package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassData
import com.example.newbookshelf.data.model.post.readingclass.AddReadingClassModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddReadingClassUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(addReadingClassData: AddReadingClassData): Resource<AddReadingClassModel> {
        return bookRepository.addReadingClass(addReadingClassData)
    }
}