package com.example.newbookshelf.data.model.post.readingclass

import com.example.newbookshelf.data.model.post.Pagination

data class ReadingClassMembersModel(
    val result: Boolean,
    val data: List<ReadingClassMembersModelData>,
    val pagination: Pagination
)