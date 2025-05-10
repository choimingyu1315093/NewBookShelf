package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GoogleMapRepository {

    suspend fun searchLatLng(address: String, key: String): Resource<GeocodingModel>
    suspend fun searchPlace(latLng: String, key: String): Resource<GeocodingModel>
}