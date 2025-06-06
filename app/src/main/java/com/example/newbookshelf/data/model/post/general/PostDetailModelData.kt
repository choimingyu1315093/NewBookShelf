package com.example.newbookshelf.data.model.post.general

data class PostDetailModelData(
    val create_date: String,
    val is_scrap: Boolean,
    val post_comments: List<PostDetailComment>,
    val post_content: String,
    val post_idx: Int,
    val post_title: String,
    val update_date: String,
    val users: PostDetailModelUsers
)