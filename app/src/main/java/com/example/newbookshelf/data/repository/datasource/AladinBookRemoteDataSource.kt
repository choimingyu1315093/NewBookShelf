package com.example.newbookshelf.data.repository.datasource

import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.model.home.Item
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AladinBookRemoteDataSource {

    fun weekBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
    fun newBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
    fun attentionBestseller(searchTarget: String, categoryId: Int): Flow<Response<BestsellerModel>>
}