package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.Flow

class KakaoSearchPlaceUseCase(private val kakaoRepository: KakaoRepository) {

    suspend fun execute(accessToken: String, q: String): Resource<KakaoMapModel> {
        return kakaoRepository.searchPlace(accessToken, q)
    }
}