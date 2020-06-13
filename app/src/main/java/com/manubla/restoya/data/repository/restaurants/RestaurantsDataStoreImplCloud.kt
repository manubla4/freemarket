package com.manubla.restoya.data.repository.restaurants

import android.content.Context
import com.bumptech.glide.Glide
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.RestaurantService
import com.manubla.restoya.data.service.response.ImagesConfigurationResponse
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import java.io.File

class RestaurantsDataStoreImplCloud(private var restaurantService: RestaurantService,
                                    private val context: Context) : RestaurantsDataStore {


    private val defaultPopularitySort = "popularity.desc"
    private val defaultPosterSize = "w185"
    private val defaultLanguage = "en-US"

    override suspend fun getMoviesPage(page: Int): RestaurantsPageResponse {
        return restaurantService.getMoviesPage(
            defaultLanguage,
            defaultPopularitySort,
            includeAdult = false,
            includeVideo = false,
            page = page
        ).apply { fromCloud = true }
    }

    override suspend fun getMoviesPage(page: Int, ratingMin: Double,
                                       ratingMax: Double): RestaurantsPageResponse {

        return restaurantService.getMoviesPage(
            defaultLanguage,
            defaultPopularitySort,
            includeAdult = false,
            includeVideo = false,
            page = page,
            ratingMin = ratingMin,
            ratingMax = ratingMax
        ).apply { fromCloud = true }
    }


    override suspend fun getMovie(movieId: Int): Restaurant? {
        TODO("not implemented")
    }

    suspend fun getRemoteImage(posterPath: String?, imageConfig: ImagesConfigurationResponse): String {
        var avgPosterSize: String = try {
            imageConfig.posterSizes[imageConfig.posterSizes.size / 2]
        } catch (e: Exception) {
            defaultPosterSize
        }
        val url = imageConfig.secureBaseUrl.plus(avgPosterSize).plus(posterPath)
        val file: File = Glide.with(context).asFile().load(url).submit().get()
        return file.absolutePath
    }

    suspend fun searchMoviesPage(query: String, page: Int): RestaurantsPageResponse {
        return restaurantService.searchMoviesPage(
            defaultLanguage,
            query,
            page = page,
            includeAdult = false
        ).apply { fromCloud = true }
    }

}