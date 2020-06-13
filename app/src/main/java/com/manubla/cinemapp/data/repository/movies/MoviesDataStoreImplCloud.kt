package com.manubla.cinemapp.data.repository.movies

import android.content.Context
import com.bumptech.glide.Glide
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.MovieService
import com.manubla.cinemapp.data.service.response.ImagesConfigurationResponse
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import java.io.File

class MoviesDataStoreImplCloud(private var movieService: MovieService,
                               private val context: Context) : MoviesDataStore {


    private val defaultPopularitySort = "popularity.desc"
    private val defaultPosterSize = "w185"
    private val defaultLanguage = "en-US"

    override suspend fun getMoviesPage(page: Int): MoviesPageResponse {
        return movieService.getMoviesPage(
            defaultLanguage,
            defaultPopularitySort,
            includeAdult = false,
            includeVideo = false,
            page = page
        ).apply { fromCloud = true }
    }

    override suspend fun getMoviesPage(page: Int, ratingMin: Double,
                                       ratingMax: Double): MoviesPageResponse {

        return movieService.getMoviesPage(
            defaultLanguage,
            defaultPopularitySort,
            includeAdult = false,
            includeVideo = false,
            page = page,
            ratingMin = ratingMin,
            ratingMax = ratingMax
        ).apply { fromCloud = true }
    }


    override suspend fun getMovie(movieId: Int): Movie? {
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

    suspend fun searchMoviesPage(query: String, page: Int): MoviesPageResponse {
        return movieService.searchMoviesPage(
            defaultLanguage,
            query,
            page = page,
            includeAdult = false
        ).apply { fromCloud = true }
    }

}