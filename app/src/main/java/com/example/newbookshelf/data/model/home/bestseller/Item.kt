package com.example.newbookshelf.data.model.home.bestseller

data class Item(
    val adult: Boolean?,
    val author: String?,
    val bestDuration: String?,
    val bestRank: Int?,
    val categoryId: Int?,
    val categoryName: String?,
    val cover: String?,
    val customerReviewRank: Int?,
    val description: String?,
    val fixedPrice: Boolean?,
    val isbn: String?,
    val isbn13: String?,
    val itemId: Int?,
    val link: String?,
    val mallType: String?,
    val mileage: Int?,
    val priceSales: Int?,
    val priceStandard: Int?,
    val pubDate: String?,
    val publisher: String?,
    val salesPoint: Int?,
    val seriesInfo: SeriesInfo?,
    val stockStatus: String?,
    val subInfo: SubInfo?,
    val title: String?
)