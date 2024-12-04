package com.example.newbookshelf.data.repository

import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.model.home.Item
import com.example.newbookshelf.data.repository.datasource.AladinBookRemoteDataSource
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class AladinBookRepositoryImpl(private val aladinBookRemoteDataSource: AladinBookRemoteDataSource): AladinBookRepository {

    override fun weekBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRemoteDataSource.weekBestseller(searchTarget, categoryId).map { response ->
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        }
    }

    override fun newBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRemoteDataSource.newBestseller(searchTarget, categoryId).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
    }

    override fun attentionBestseller(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRemoteDataSource.attentionBestseller(searchTarget, categoryId).map { response ->
            if(response.isSuccessful){
                Resource.Success(response.body()!!)
            }else {
                Resource.Error(response.message())
            }
        }
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