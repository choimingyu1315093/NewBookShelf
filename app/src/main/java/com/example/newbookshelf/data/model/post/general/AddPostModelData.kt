package com.example.newbookshelf.data.model.post.general

data class AddPostModelData(
    val create_date: String,
    val post_content: String,
    val post_idx: Int,
    val post_title: String,
    val update_date: String,
    val users: PostCommentModelUsers
)