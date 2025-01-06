package com.example.newbookshelf.data.model.post.general

data class PostDetailComment(
    val comment_content: String,
    val create_date: String,
    val post_comment_idx: Int,
    val update_date: String,
    val users: PostDetailModelUsers
)