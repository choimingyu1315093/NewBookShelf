package com.example.newbookshelf.data.model.post.general

data class PostModel(
    val result: Boolean,
    val data: List<PostModelData>,
    val pagination: Pagination
)