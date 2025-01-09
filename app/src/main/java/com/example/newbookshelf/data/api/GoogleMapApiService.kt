package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapApiService {

    //위도, 경도 조회
    @GET("geocode/json")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Response<GeocodingModel>
}

