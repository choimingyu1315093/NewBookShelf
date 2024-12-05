package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.bestseller.BestsellerModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import kotlinx.coroutines.flow.Flow

class WeekBestsellerUseCase(private val aladinBookRepository: AladinBookRepository) {

    fun execute(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRepository.weekBestseller(searchTarget, categoryId)
    }
}