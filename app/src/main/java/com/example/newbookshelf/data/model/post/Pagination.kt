package com.example.newbookshelf.data.model.post

data class Pagination(
    val block: Int,
    val current_block: Int,
    val current_page: Int,
    val limit: Int,
    val total: Int,
    val total_block: Int,
    val total_page: Int
)