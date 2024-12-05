package com.example.newbookshelf.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newbookshelf.data.db.SearchedBookDAO
import com.example.newbookshelf.data.db.SearchedBookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSearchedBookDatabase(app: Application): SearchedBookDatabase {
        return Room
            .databaseBuilder(app, SearchedBookDatabase::class.java, "searched_book_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchedBookDAO(searchedBookDatabase: SearchedBookDatabase): SearchedBookDAO {
        return searchedBookDatabase.getSearchedBookDao()
    }
}