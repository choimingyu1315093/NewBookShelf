package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.general.PostCommentData
import com.example.newbookshelf.data.model.post.general.PostCommentModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class PostCommentUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(postCommentData: PostCommentData): Resource<PostCommentModel> {
        return bookRepository.postComment(postCommentData)
    }
}