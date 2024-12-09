package com.example.newbookshelf.data.model.home.searchbook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "searched_books", indices = [Index(value = ["title"], unique = true)] )
data class SearchedBook(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "title")
    val title: String
)
