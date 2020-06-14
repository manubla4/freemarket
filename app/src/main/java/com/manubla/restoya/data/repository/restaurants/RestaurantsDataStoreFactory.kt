package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.dao.RestaurantDao
import com.manubla.restoya.data.helper.networking.NetworkingManager
import com.manubla.restoya.data.service.RestaurantService

@Suppress("UNUSED_PARAMETER")
open class RestaurantsDataStoreFactory(
    private var service: RestaurantService,
    private var dao: RestaurantDao,
    private var networkingManager: NetworkingManager
) {

    val restaurantsDataStoreFactory: RestaurantsDataStore
        get() = createDataStoreFactory()

    private fun createDataStoreFactory() =
        if (networkingManager.isNetworkOnline())
            createDataStoreCloud()
        else
            createDataStoreDatabase()

    private fun createDataStoreCloud() = RestaurantsDataStoreImplCloud(service, createDataStoreDatabase())
    private fun createDataStoreDatabase() = RestaurantsDataStoreImplDatabase(dao)
}