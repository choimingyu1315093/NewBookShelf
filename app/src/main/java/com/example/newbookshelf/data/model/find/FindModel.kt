package com.example.newbookshelf.data.model.find

data class FindModel(
    val result: Boolean,
    val data: FindModelData,
)

data class FindModelData(
    val data: String,
    val result: Boolean
)