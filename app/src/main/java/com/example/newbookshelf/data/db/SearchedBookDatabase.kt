package com.example.newbookshelf.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook

@Database(entities = [SearchedBook::class], version = 1)
abstract class SearchedBookDatabase: RoomDatabase() {
    abstract fun getSearchedBookDao(): SearchedBookDAO
}