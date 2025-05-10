package com.example.newbookshelf.data.repository

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.repository.datasource.GoogleMapDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.GoogleMapRepository
import com.example.newbookshelf.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class GoogleMapRepositoryImpl(private val googleMapDataSource: GoogleMapDataSource): GoogleMapRepository {

    override suspend fun searchLatLng(address: String, key: String): Resource<GeocodingModel> {
        return responseToResource(googleMapDataSource.searchLatLng(address, key))
    }

    override suspend fun searchPlace(latLng: String, key: String): Resource<GeocodingModel> {
        return responseToResource(googleMapDataSource.searchPlace(latLng, key))
    }

    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                Resource.Success(result)
            } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error(response.message())
        }
    }
}
