package com.manubla.cinemapp.data.repository.genres

import android.accounts.NetworkErrorException
import com.manubla.cinemapp.data.dao.GenreDao
import com.manubla.cinemapp.data.helper.networking.NetworkingManager
import com.manubla.cinemapp.data.repository.genre.GenresDataStore
import com.manubla.cinemapp.data.repository.genre.GenresDataStoreImplCloud
import com.manubla.cinemapp.data.repository.genre.GenresDataStoreImplDatabase
import com.manubla.cinemapp.data.service.GenreService

@Suppress("UNUSED_PARAMETER")
open class GenresDataStoreFactory(
    var service: GenreService,
    var dao: GenreDao,
    var networkingManager: NetworkingManager
) {

    val genresDataStoreFactory: GenresDataStore
        get() = createDataStoreFactory()
    val genresDataStoreCloud: GenresDataStoreImplCloud
        get() = createDataStoreCloud()
    val genresDataStoreDatabase: GenresDataStoreImplDatabase
        get() = createDataStoreDatabase()


    private fun createDataStoreFactory() =
        if (networkingManager.isNetworkOnline())
            createDataStoreCloud()
        else
            createDataStoreDatabase()

    private fun createDataStoreCloud() =
        if (networkingManager.isNetworkOnline())
            GenresDataStoreImplCloud(service)
        else
            throw NetworkErrorException()

    private fun createDataStoreDatabase() = GenresDataStoreImplDatabase(dao)

}