package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

interface RestaurantsDataStore {
    suspend fun getRestaurantsPage(latitude: Double?,
                                   longitude: Double?,
                                   offset: Int): RestaurantsPageResponse
}