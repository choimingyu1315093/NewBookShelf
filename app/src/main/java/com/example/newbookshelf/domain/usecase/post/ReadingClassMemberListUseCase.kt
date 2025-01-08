package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.readingclass.ReadingClassMembersModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository

class ReadingClassMemberListUseCase(private val bookRepository: BookRepository) {

    suspend fun execute(readingClassIdx: Int, limit: Int, currentPage: Int): Resource<ReadingClassMembersModel> {
        return bookRepository.readingClassMemberList(readingClassIdx, limit, currentPage)
    }
}