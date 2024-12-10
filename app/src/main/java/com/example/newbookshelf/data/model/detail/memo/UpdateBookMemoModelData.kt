package com.example.newbookshelf.data.model.detail.memo

data class UpdateBookMemoModelData(
    val affected: Int,
    val generatedMaps: List<Any>,
    val raw: List<Any>
)