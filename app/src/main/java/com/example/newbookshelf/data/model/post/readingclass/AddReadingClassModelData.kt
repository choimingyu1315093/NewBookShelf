package com.example.newbookshelf.data.model.post.readingclass

data class AddReadingClassModelData(
    val books: AddReadingClassModelBooks,
    val club_latitude: String,
    val club_longitude: String,
    val club_meet_date: String,
    val club_post_idx: Int,
    val club_result_image: String,
    val club_state: String,
    val create_date: String,
    val delete_date: String?,
    val post_content: String,
    val post_title: String,
    val update_date: String,
    val users: Users
)