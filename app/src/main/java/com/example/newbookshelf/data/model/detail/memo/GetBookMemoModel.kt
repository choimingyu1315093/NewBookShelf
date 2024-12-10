package com.example.newbookshelf.data.model.detail.memo

data class GetBookMemoModel(
    val result: Boolean,
    val data: List<GetBookMemoModelData>
)