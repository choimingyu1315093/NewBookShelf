package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface GoogleMapRepository {

    suspend fun searchLatLng(address: String, key: String): Resource<GeocodingModel>
}