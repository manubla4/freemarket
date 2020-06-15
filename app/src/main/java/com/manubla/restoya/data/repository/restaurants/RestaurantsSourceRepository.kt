package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.response.RestaurantsPageResponse

interface RestaurantsSourceRepository {
    suspend fun getRestaurantsPage(latitude: Double?,
                                   longitude: Double?,
                                   offset: Int): RestaurantsPageResponse

    suspend fun getAllStoredRestaurants(): List<Restaurant>
}