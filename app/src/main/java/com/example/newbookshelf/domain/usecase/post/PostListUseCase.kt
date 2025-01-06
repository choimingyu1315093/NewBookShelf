package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.general.PostModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class PostListUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(userIdx: Int, limit: Int, currentPage: Int): Resource<PostModel> {
        return bookRepository.postList(userIdx, limit, currentPage)
    }
}