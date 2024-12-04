package com.example.newbookshelf.domain.repository

import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.model.home.Item
import com.example.newbookshelf.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface AladinBookRepository {

    fun weekBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>>
    fun newBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>>
    fun attentionBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>>
}