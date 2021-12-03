package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.dao.RestaurantDao
import com.manubla.freemarket.data.helper.networking.NetworkingManager
import com.manubla.freemarket.data.service.RestaurantService

@Suppress("UNUSED_PARAMETER")
open class RestaurantsDataStoreFactory(
    private val service: RestaurantService,
    private val dao: RestaurantDao,
    private val networkingManager: NetworkingManager
) {

    val restaurantsDataStoreFactory: RestaurantsDataStore
        get() = createDataStoreFactory()
    val restaurantsDataStoreDatabase: RestaurantsDataStoreImplDatabase
        get() = createDataStoreDatabase()

    private fun createDataStoreFactory() =
        if (networkingManager.isNetworkOnline())
            createDataStoreCloud()
        else
            createDataStoreDatabase()

    private fun createDataStoreCloud() = RestaurantsDataStoreImplCloud(service, createDataStoreDatabase())
    private fun createDataStoreDatabase() = RestaurantsDataStoreImplDatabase(dao)
}