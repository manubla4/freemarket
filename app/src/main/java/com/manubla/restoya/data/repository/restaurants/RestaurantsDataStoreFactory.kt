package com.manubla.restoya.data.repository.restaurants

import android.accounts.NetworkErrorException
import android.content.Context
import com.manubla.restoya.data.dao.RestaurantDao
import com.manubla.restoya.data.helper.networking.NetworkingManager
import com.manubla.restoya.data.service.RestaurantService

@Suppress("UNUSED_PARAMETER")
open class RestaurantsDataStoreFactory(
    var context: Context,
    var service: RestaurantService,
    var dao: RestaurantDao,
    private var networkingManager: NetworkingManager
) {

    val restaurantsDataStoreFactory: RestaurantsDataStore
        get() = createDataStoreFactory()
    val moviesDataStoreCloud: RestaurantsDataStoreImplCloud
        get() = createDataStoreCloud()
    val moviesDataStoreDatabase: RestaurantsDataStoreImplDatabase
        get() = createDataStoreDatabase()


    private fun createDataStoreFactory() =
        if (networkingManager.isNetworkOnline())
            createDataStoreCloud()
        else
            createDataStoreDatabase()

    private fun createDataStoreCloud() =
        if (networkingManager.isNetworkOnline())
            RestaurantsDataStoreImplCloud(service, context)
        else
            throw NetworkErrorException()

    private fun createDataStoreDatabase() = RestaurantsDataStoreImplDatabase(dao)

}