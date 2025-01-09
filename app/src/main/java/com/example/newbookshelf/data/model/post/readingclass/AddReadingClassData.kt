package com.example.newbookshelf.data.model.post.readingclass

data class AddReadingClassData(
    val book_isbn: String,
    val club_latitude: Double,
    val club_longitude: Double,
    val club_meet_date: String,
    val post_content: String,
    val post_title: String
)