package com.example.newbookshelf.data.model.post.google

data class GeocodingModel(
    val results: List<Result>,
    val status: String
)