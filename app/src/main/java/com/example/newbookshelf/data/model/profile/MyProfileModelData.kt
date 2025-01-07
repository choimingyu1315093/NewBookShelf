package com.example.newbookshelf.data.model.profile

data class MyProfileModelData(
    val relation_count: Int,
    val relation_request_count: Int,
    val user_description: String,
    val user_grade: String,
    val user_idx: Int,
    val user_max_point: Int,
    val user_name: String,
    val user_point: Int,
    val top1_book: Top1Book?,
    val top2_book: Top1Book?,
    val top3_book: Top1Book?,
)