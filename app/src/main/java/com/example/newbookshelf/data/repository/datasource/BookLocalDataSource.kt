package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import kotlinx.coroutines.flow.Flow

interface BookLocalDataSource {
    fun getSearchedBook(): Flow<List<SearchedBook>>
    suspend fun insert(searchedBook: SearchedBook)
    suspend fun delete(searchedBook: SearchedBook)
    suspend fun allDelete()
}