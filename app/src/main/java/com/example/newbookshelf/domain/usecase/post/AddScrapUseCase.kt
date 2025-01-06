package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.AddScrapData
import com.example.newbookshelf.data.model.post.AddScrapModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddScrapUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(addScrapData: AddScrapData): Resource<AddScrapModel> {
        return bookRepository.addScrap(addScrapData)
    }
}