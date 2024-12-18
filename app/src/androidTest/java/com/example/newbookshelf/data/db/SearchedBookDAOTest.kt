package com.example.newbookshelf.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Database
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchedBookDAOTest {
    private lateinit var searchedBookDAO: SearchedBookDAO
    private lateinit var searchedBookDatabase: SearchedBookDatabase

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        searchedBookDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SearchedBookDatabase::class.java
        ).build()

        searchedBookDAO = searchedBookDatabase.getSearchedBookDao()
    }

    @Test
    fun insertSearchedBookTest() = runBlocking{
        val searchedBook = SearchedBook(1,"title1")
        searchedBookDAO.insert(searchedBook)

        val result = searchedBookDAO.getSearchedBook().first()
        Truth.assertThat(result[0]).isEqualTo(searchedBook)
    }

    @Test
    fun deleteSearchedBookTest() = runBlocking {
        val searchedBook = SearchedBook(1,"title1")
        searchedBookDAO.insert(searchedBook)
        searchedBookDAO.delete(searchedBook)

        val result = searchedBookDAO.delete(searchedBook)
        Truth.assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun allDeleteSearchedBookTest() = runBlocking {
        val searchedBook = SearchedBook(1,"title1")
        val searchedBook2 = SearchedBook(2,"title2")
        searchedBookDAO.insert(searchedBook)
        searchedBookDAO.insert(searchedBook2)
        searchedBookDAO.allDelete()

        val result = searchedBookDAO.allDelete()
        Truth.assertThat(result).isEqualTo(Unit)
    }

    @After
    fun tearDown(){
        searchedBookDatabase.close()
    }
}