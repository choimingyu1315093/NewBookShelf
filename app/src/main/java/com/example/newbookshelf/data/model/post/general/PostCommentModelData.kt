package com.example.newbookshelf.data.model.post.general

data class PostCommentModelData(
    val comment_content: String,
    val create_date: String,
    val post_comment_idx: Int,
    val post_idx: Int,
    val posts: PostCommentModelPosts,
    val update_date: String,
    val users: PostCommentModelUsers
)