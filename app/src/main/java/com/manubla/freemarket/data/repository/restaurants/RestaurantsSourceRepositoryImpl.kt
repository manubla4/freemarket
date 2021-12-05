package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

class RestaurantsSourceRepositoryImpl(var factory: RestaurantsDataStoreFactory) : RestaurantsSourceRepository {

    override suspend fun getRestaurantsPage(latitude: Double?,
                                            longitude: Double?,
                                            offset: Int
                                            ): RestaurantsPageResponse =
        try {
            factory.restaurantsDataStoreFactory
                                .getRestaurantsPage(latitude, longitude, offset)
        } catch (error: Exception) {
            RestaurantsPageResponse(total = 0,
                                    max = 0,
                                    sort = "",
                                    count = 0,
                                    data = listOf(),
                                    offset = offset)
        }


    override suspend fun getAllStoredRestaurants(): List<Product> =
        try {
            factory.restaurantsDataStoreDatabase.getAllRestaurants()
        } catch (error: Exception) {
            listOf()
        }

}