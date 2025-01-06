package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.general.AddPostData
import com.example.newbookshelf.data.model.post.general.AddPostModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class AddPostUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(addPostData: AddPostData): Resource<AddPostModel> {
        return bookRepository.addPost(addPostData)
    }
}