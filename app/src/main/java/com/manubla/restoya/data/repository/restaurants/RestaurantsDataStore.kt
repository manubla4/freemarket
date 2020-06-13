package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.response.RestaurantsPageResponse

interface RestaurantsDataStore {
    suspend fun getMoviesPage(page: Int): RestaurantsPageResponse
    suspend fun getMoviesPage(page: Int, ratingMin: Double, ratingMax: Double): RestaurantsPageResponse
    suspend fun getMovie(movieId: Int): Restaurant?

}