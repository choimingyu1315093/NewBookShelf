package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.home.bestseller.BestsellerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AladinApiService {
    //이번 주 베스트셀러
    @GET("ItemList.aspx")
    suspend fun weekBestseller(
        @Query("ttbkey") ttbkey: String = "ttbthdwhddnd4271541001",
        @Query("QueryType") queryType: String = "Bestseller",
        @Query("MaxResults") maxResults: String = "30",
        @Query("start") start: Int = 1,
        @Query("SearchTarget") searchTarget: String,
        @Query("output") output: String = "js",
        @Query("Version") version: String = "20131101",
        @Query("Cover") cover: String = "Big",
        @Query("CategoryId") categoryId: Int
    ): Response<BestsellerModel>

    //신간 목록
    @GET("ItemList.aspx")
    suspend fun newBestsellerList(
        @Query("ttbkey") ttbkey: String = "ttbthdwhddnd4271541001",
        @Query("QueryType") queryType: String = "ItemNewAll",
        @Query("MaxResults") maxResults: String = "30",
        @Query("start") start: Int = 1,
        @Query("SearchTarget") searchTarget: String,
        @Query("output") output: String = "js",
        @Query("Version") version: String = "20131101",
        @Query("Cover") cover: String = "Big",
        @Query("CategoryId") categoryId: Int
    ): Response<BestsellerModel>

    //신간 주목
    @GET("ItemList.aspx")
    suspend fun attentionBestsellerList(
        @Query("ttbkey") ttbkey: String = "ttbthdwhddnd4271541001",
        @Query("QueryType") queryType: String = "ItemNewSpecial",
        @Query("MaxResults") maxResults: String = "30",
        @Query("start") start: Int = 1,
        @Query("SearchTarget") searchTarget: String,
        @Query("output") output: String = "js",
        @Query("Version") version: String = "20131101",
        @Query("Cover") cover: String = "Big",
        @Query("CategoryId") categoryId: Int
    ): Response<BestsellerModel>
}