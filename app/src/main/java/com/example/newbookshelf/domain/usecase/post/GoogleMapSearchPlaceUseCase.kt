package com.example.newbookshelf.domain.usecase.post

import com.example.newbookshelf.data.model.post.google.GeocodingModel
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.domain.repository.GoogleMapRepository
import com.google.maps.model.LatLng

class GoogleMapSearchPlaceUseCase(private val googleMapRepository: GoogleMapRepository) {

    suspend fun execute(latLng: String, key: String): Resource<GeocodingModel> {
        return googleMapRepository.searchPlace(latLng, key)
    }
}