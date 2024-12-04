package com.example.newbookshelf.data.repository.datasourceimpl

import com.example.newbookshelf.data.api.AladinApiService
import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.model.home.Item
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.presentation.di.AladinRetrofit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class AladinBookRemoteDataSourceImpl(@AladinRetrofit private val aladinApiService: AladinApiService): AladinBookRemoteDataSource {

    override fun weekBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>> {
        return flow {
            emit(aladinApiService.weekBestseller(searchTarget = searchTarget, categoryId = categoryId))
        }
    }

    override fun newBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>> {
        return flow {
            emit(aladinApiService.newBestsellerList(searchTarget = searchTarget, categoryId = categoryId))
        }
    }

    override fun attentionBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>> {
        return flow {
            emit(aladinApiService.attentionBestsellerList(searchTarget = searchTarget, categoryId = categoryId))
        }
    }
}