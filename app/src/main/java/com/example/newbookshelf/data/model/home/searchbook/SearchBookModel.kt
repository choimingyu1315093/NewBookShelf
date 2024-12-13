package com.example.newbookshelf.data.model.home.searchbook

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchBookModel(
    val result: Boolean,
    val data: SearchBookModelData
): Parcelable