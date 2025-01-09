package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.GoogleMapRepository

class GoogleMapSearchLatLngUseCase(private val googleMapRepository: GoogleMapRepository) {

    suspend fun execute(address: String, key: String): Resource<GeocodingModel> {
        return googleMapRepository.searchLatLng(address, key)
    }
}