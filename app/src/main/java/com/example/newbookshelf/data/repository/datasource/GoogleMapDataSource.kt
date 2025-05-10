package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GoogleMapDataSource {

    suspend fun searchLatLng(address: String, key: String): Response<GeocodingModel>
    suspend fun searchPlace(latLng: String, key: String): Response<GeocodingModel>
}