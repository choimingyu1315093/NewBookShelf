package com.example.newbookshelf.data.model.post.general

import com.example.newbookshelf.data.model.post.Pagination

data class PostModel(
    val result: Boolean,
    val data: List<PostModelData>,
    val pagination: Pagination
)