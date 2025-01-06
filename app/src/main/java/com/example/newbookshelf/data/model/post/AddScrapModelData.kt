package com.example.newbookshelf.data.model.post

import com.example.newbookshelf.data.model.post.general.PostCommentModelPosts

data class AddScrapModelData(
    val posts: PostCommentModelPosts,
    val scrap_idx: String,
    val users: AddScrapModelUsers
)