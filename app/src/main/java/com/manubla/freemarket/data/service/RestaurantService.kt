package com.manubla.freemarket.data.service

import com.manubla.freemarket.data.service.response.RestaurantsPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {

    @GET("search/restaurants")
    suspend fun getRestaurantsPage(@Query("point") point: String,
                                   @Query("country") country: Int,
                                   @Query("offset") offset: Int,
                                   @Query("max") max: Int ,
                                   @Query("fields") fields: String): RestaurantsPageResponse

}
