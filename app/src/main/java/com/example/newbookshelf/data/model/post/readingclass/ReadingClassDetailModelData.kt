package com.example.newbookshelf.data.model.post.readingclass

data class ReadingClassDetailModelData(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val club_latitude: String,
    val club_longitude: String,
    val club_meet_date: String,
    val club_post_idx: Int,
    val club_result_image: String,
    val create_date: String,
    val is_scrap: Boolean,
    val post_content: String,
    val post_title: String,
    val update_date: String,
    val user_idx: Int,
    val user_name: String,
    val user_type: String
)