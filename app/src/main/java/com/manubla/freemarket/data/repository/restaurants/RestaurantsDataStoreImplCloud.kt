package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.service.RestaurantService
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

class RestaurantsDataStoreImplCloud(private var restaurantService: RestaurantService,
                                    private var restaurantsDataStoreDatabase: RestaurantsDataStoreImplDatabase): RestaurantsDataStore {

    private val defaultCountryId    = 1  //Uruguay
    private val defaultPageSize     = 20
    private val defaultFields       = "id,name,logo,coordinates,distance,discount,paymentMethods," +
                                      "generalScore,allCategories,businessType,hasOnlinePaymentMethods," +
                                      "deliveryTimeMinMinutes,deliveryTimeMaxMinutes"

    override suspend fun getRestaurantsPage(latitude: Double?,
                   longitude: Double?, offset: Int): RestaurantsPageResponse {

        lateinit var result: RestaurantsPageResponse
        try {
            val coordinates = "$latitude,$longitude"
            result = restaurantService.getRestaurantsPage(
                point = coordinates,
                country = defaultCountryId,
                offset = offset,
                max = defaultPageSize,
                fields = defaultFields
            )
        } catch (error: Exception) {
            return restaurantsDataStoreDatabase.getRestaurantsPage(latitude, longitude, offset)
        }
        if (offset == 0) restaurantsDataStoreDatabase.deleteRestaurants()
        restaurantsDataStoreDatabase.storeRestaurants(result.data)
        return result
    }

}