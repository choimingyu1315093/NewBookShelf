package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.db.SearchedBookDAO
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.repository.datasource.BookLocalDataSource
import kotlinx.coroutines.flow.Flow

class BookLocalDataSourceImpl(private val searchedBookDAO: SearchedBookDAO): BookLocalDataSource {
    override fun getSearchedBook(): Flow<List<SearchedBook>> {
        return searchedBookDAO.getSearchedBook()
    }

    override suspend fun insert(searchedBook: SearchedBook) {
        searchedBookDAO.insert(searchedBook)
    }

    override suspend fun delete(searchedBook: SearchedBook) {
        searchedBookDAO.delete(searchedBook)
    }

    override suspend fun allDelete() {
        searchedBookDAO.allDelete()
    }
}