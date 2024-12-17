package com.example.newbookshelf.data.repository

import android.util.Log
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.model.post.kakao.KakaoMapModel
import com.example.newbookshelf.data.model.profile.MyProfileModel
import com.example.newbookshelf.data.repository.datasource.BookLocalDataSource
import com.example.newbookshelf.data.repository.datasource.BookRemoteDataSource
import com.example.newbookshelf.data.repository.datasource.KakaoDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.BookRepository
import com.example.newbookshelf.domain.repository.KakaoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KakaoRepositoryImpl(private val kakaoDataSource: KakaoDataSource): KakaoRepository {

    override fun searchPlace(accessToken: String, q: String): Flow<Resource<KakaoMapModel>> {
        return kakaoDataSource.searchPlace(accessToken, q).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }
}