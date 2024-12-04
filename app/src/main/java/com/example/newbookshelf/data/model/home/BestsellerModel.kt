package com.example.newbookshelf.data.model.home

data class BestsellerModel(
    val item: List<Item>?,
    val itemsPerPage: Int?,
    val link: String?,
    val logo: String?,
    val pubDate: String?,
    val query: String?,
    val searchCategoryId: Int?,
    val searchCategoryName: String?,
    val startIndex: Int?,
    val title: String?,
    val totalResults: Int?,
    val version: String?
)