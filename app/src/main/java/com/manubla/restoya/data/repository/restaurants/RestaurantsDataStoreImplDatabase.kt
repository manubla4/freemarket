package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.dao.RestaurantDao
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.response.RestaurantsPageResponse

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