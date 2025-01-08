package com.example.newbookshelf.data.model.post.readingclass

data class ReadingClassJoinModelData(
    val club_member_idx: Int,
    val club_posts: ReadingClassJoinModelPosts,
    val is_participation: String,
    val users: Users
)