package com.example.newbookshelf.data.repository

import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.KakaoRepository
import retrofit2.Response

class KakaoRepositoryImpl(private val kakaoDataSource: KakaoDataSource): KakaoRepository {

    override suspend fun searchPlace(accessToken: String, q: String): Resource<KakaoMapModel> {
        return responseToResource(kakaoDataSource.searchPlace(accessToken, q))
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