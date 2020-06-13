package com.manubla.cinemapp.data.repository.configuration

import android.accounts.NetworkErrorException
import android.content.SharedPreferences
import com.manubla.cinemapp.data.helper.networking.NetworkingManager
import com.manubla.cinemapp.data.repository.movies.MoviesDataStoreImplDatabase
import com.manubla.cinemapp.data.service.ConfigurationService

@Suppress("UNUSED_PARAMETER")
open class ConfigurationDataStoreFactory(
    var service: ConfigurationService,
    var networkingManager: NetworkingManager,
    var sharedPreferences: SharedPreferences
) {

    val configurationDataStoreDatabase: ConfigurationDataStoreImplDatabase
        get() = createDataStoreDatabase()
    val configurationDataStoreCloud: ConfigurationDataStoreImplCloud
        get() = createDataStoreCloud()


    private fun createDataStoreCloud() =
        if (networkingManager.isNetworkOnline())
            ConfigurationDataStoreImplCloud(service)
        else
            throw NetworkErrorException()

    private fun createDataStoreDatabase() = ConfigurationDataStoreImplDatabase(sharedPreferences)




}