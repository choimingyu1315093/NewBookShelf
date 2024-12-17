package com.example.newbookshelf.data.api

import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {

    //카카오 주소 검색
    @GET("v2/local/search/keyword.json")
    suspend fun searchPlace(
        @Header("Authorization") accessToken: String,
        @Query("query") q: String
    ): Response<KakaoMapModel>
}