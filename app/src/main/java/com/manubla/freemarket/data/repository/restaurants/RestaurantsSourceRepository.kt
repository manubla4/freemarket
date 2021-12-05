package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

interface RestaurantsSourceRepository {
    suspend fun getRestaurantsPage(latitude: Double?,
                                   longitude: Double?,
                                   offset: Int): RestaurantsPageResponse

    suspend fun getAllStoredRestaurants(): List<Product>
}