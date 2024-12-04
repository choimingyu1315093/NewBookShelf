package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import kotlinx.coroutines.flow.Flow

class NewBestsellerUseCase(private val aladinBookRepository: AladinBookRepository) {

    suspend fun execute(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRepository.newBestseller(searchTarget, categoryId)
    }
}