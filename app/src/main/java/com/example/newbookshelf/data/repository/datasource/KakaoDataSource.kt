package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface KakaoDataSource {

    fun searchPlace(accessToken: String, q: String): Flow<Response<KakaoMapModel>>
}