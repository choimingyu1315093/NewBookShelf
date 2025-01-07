package com.example.newbookshelf.data.model.post.readingclass

import com.example.newbookshelf.data.model.post.Pagination

data class ReadingClassModel(
    val result: Boolean,
    val data: List<ReadingClassModelData>,
    val pagination: Pagination
)