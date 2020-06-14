package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.service.response.RestaurantsPageResponse

class RestaurantsSourceRepositoryImpl(var factory: RestaurantsDataStoreFactory) : RestaurantsSourceRepository {

    override suspend fun getRestaurantsPage(latitude: Double,
                                            longitude: Double,
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

}