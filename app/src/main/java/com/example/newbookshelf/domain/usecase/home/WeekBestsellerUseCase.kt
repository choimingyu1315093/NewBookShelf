package com.example.newbookshelf.domain.usecase.home

import com.example.newbookshelf.data.model.home.BestsellerModel
import com.example.newbookshelf.data.model.home.Item
import com.example.newbookshelf.data.model.login.LoginData
import com.example.newbookshelf.data.model.login.LoginModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.AladinBookRepository
import com.example.newbookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class WeekBestsellerUseCase(private val aladinBookRepository: AladinBookRepository) {

    suspend fun execute(searchTarget: String, categoryId: Int): Flow<Resource<BestsellerModel>> {
        return aladinBookRepository.weekBestseller(searchTarget, categoryId)
    }
}