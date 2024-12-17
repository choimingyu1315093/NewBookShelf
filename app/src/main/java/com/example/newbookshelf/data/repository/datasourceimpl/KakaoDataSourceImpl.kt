package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.ApiService
import com.example.newbookshelf.data.api.KakaoApiService
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.presentation.di.DefaultRetrofit
import com.example.newbookshelf.presentation.di.KakaoRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class KakaoDataSourceImpl(@KakaoRetrofit private val apiService: KakaoApiService): KakaoDataSource {

    override fun searchPlace(accessToken: String, q: String): Flow<Response<KakaoMapModel>> {
        return flow {
            emit(apiService.searchPlace(accessToken, q = q))
        }
    }
}

