package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.home.bestseller.BestsellerModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AladinBookRemoteDataSource {

    fun weekBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
    fun newBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
    fun attentionBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
}