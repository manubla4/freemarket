package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.dao.RestaurantDao
import com.manubla.freemarket.data.model.Restaurant
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

class RestaurantsDataStoreImplDatabase(private val restaurantDao: RestaurantDao) : RestaurantsDataStore {

    private val defaultPageSize = 20

    override suspend fun getRestaurantsPage(latitude: Double?,
                                            longitude: Double?,
                                            offset: Int): RestaurantsPageResponse {
        val results = restaurantDao.getAllWithOffset(offset, defaultPageSize)
        val total = restaurantDao.getRowsCount()
        return RestaurantsPageResponse(total = total,
                                       max = defaultPageSize,
                                       sort = "",
                                       count = results.size,
                                       data = results,
                                       offset = offset)
    }


    suspend fun getAllRestaurants(): List<Restaurant> =
        restaurantDao.getAll()

    
    suspend fun storeRestaurants(restaurants: List<Restaurant>) {
        restaurantDao.insertAll(restaurants)
    }

    suspend fun deleteRestaurants() {
        restaurantDao.deleteAll()
    }

}