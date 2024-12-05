package com.example.newbookshelf.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchedBookDAO {
    @Query("SELECT * FROM searched_books")
    fun getSearchedBook(): Flow<List<SearchedBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchedBook: SearchedBook)

    @Delete
    suspend fun delete(searchedBook: SearchedBook)

    @Query("DELETE FROM searched_books")
    suspend fun allDelete()
}