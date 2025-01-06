package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.general.PostDetailModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class PostDetailUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(postIdx: Int): Resource<PostDetailModel> {
        return bookRepository.postDetail(postIdx)
    }
}