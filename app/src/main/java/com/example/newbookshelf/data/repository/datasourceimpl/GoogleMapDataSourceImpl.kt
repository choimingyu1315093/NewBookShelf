package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.GoogleMapApiService
import com.example.newbookshelf.data.api.KakaoApiService
import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.repository.datasource.GoogleMapDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.presentation.di.GoogleRetrofit
import com.example.newbookshelf.presentation.di.KakaoRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class GoogleMapDataSourceImpl(@GoogleRetrofit private val apiService: GoogleMapApiService): GoogleMapDataSource {

    override suspend fun searchLatLng(address: String, key: String): Response<GeocodingModel> {
        return apiService.getCoordinates(address, key)
    }
}