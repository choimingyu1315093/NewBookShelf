package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {

    fun searchPlace(accessToken: String, q: String): Flow<Resource<KakaoMapModel>>
}