package com.example.newbookshelf.data.model.detail.review

data class AddBookReviewModelData(
    val generatedMaps: List<AddBookReviewModelGeneratedMap>,
    val identifiers: List<AddBookReviewModelIdentifier>,
    val raw: AddBookReviewModelRaw
)