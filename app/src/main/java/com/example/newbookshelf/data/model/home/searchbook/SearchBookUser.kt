package com.example.newbookshelf.data.model.home.searchbook

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchBookUser(
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
): Parcelable
