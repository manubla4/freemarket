package com.manubla.restoya.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.repository.configuration.ConfigurationSourceRepository
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.restoya.data.service.response.ConfigurationResponse
import com.manubla.restoya.data.service.response.ImagesConfigurationResponse
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val restaurantsRepository: RestaurantsSourceRepository,
                    private val configRepository: ConfigurationSourceRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val data: LiveData<RestaurantsPageResponse>
        get() = localData

    private val localData = MutableLiveData<RestaurantsPageResponse>()
    private var config: ConfigurationResponse? = null
    private val defaultPosterSize = "w185"

    fun loadData(page: Int) {
        launch(Dispatchers.IO) {
            val moviesPage = getMoviesPage(page)

            if (moviesPage.fromCloud) {
                if (config == null)
                    config = getConfiguration()

                localData.postValue(initUrls(moviesPage))

                storeMovies(moviesPage.results)
                for (movie in moviesPage.results) {
                    storeMovieGenres(movie)
                    config?.let {
                        getRemoteImage(movie, it.images)
                    }
                }
            }
            else
                localData.postValue(moviesPage)
        }
    }


    fun loadFilteredData(page: Int, ratingMin: Double, ratingMax: Double) {
        launch(Dispatchers.IO) {
            val moviesPage = getMoviesPage(page, ratingMin, ratingMax)

            if (moviesPage.fromCloud) {
                if (config == null)
                    config = getConfiguration()

                localData.postValue(initUrls(moviesPage))

                storeMovies(moviesPage.results)
                for (movie in moviesPage.results) {
                    storeMovieGenres(movie)
                    config?.let {
                        getRemoteImage(movie, it.images)
                    }
                }
            } else
                localData.postValue(moviesPage)
        }
    }

    fun searchMovies(query: String, page: Int) {
        launch(Dispatchers.IO) {
            val moviesPage = initUrls(searchMoviesPage(query, page))
            localData.postValue(moviesPage)
        }
    }


    private fun initUrls(restaurantsPage: RestaurantsPageResponse): RestaurantsPageResponse {
        for (movie in restaurantsPage.results) {
            config?.let {
                val avgPosterSize: String = try {
                    it.images.posterSizes[it.images.posterSizes.size / 2]
                } catch (e: Exception) {
                    defaultPosterSize
                }
                val url = it.images.secureBaseUrl.plus(avgPosterSize).plus(movie.posterPath)
                movie.posterLocalPath = url
            }
        }
        return restaurantsPage
    }



    private suspend fun storeMovieGenres(restaurant: Restaurant) {
        try {
            restaurantsRepository.storeMovieGenres(restaurant)
        } catch (error: Exception) {
        }
    }

    private suspend fun storeMovies(restaurants: List<Restaurant>) {
        try {
            restaurantsRepository.storeMovies(restaurants)
        } catch (error: Exception) {
        }
    }

    private suspend fun getMoviesPage(page: Int): RestaurantsPageResponse = try {
        restaurantsRepository.getMoviesPage(page)
    } catch (error: Exception) {
        RestaurantsPageResponse(page, listOf(), false)
    }

    private suspend fun getMoviesPage(page: Int, ratingMin: Double, ratingMax: Double): RestaurantsPageResponse = try {
        restaurantsRepository.getMoviesPage(page, ratingMin, ratingMax)
    } catch (error: Exception) {
        RestaurantsPageResponse(page, listOf(), false)
    }

    private suspend fun searchMoviesPage(query: String, page: Int): RestaurantsPageResponse = try {
        restaurantsRepository.searchMoviesPage(query, page)
    } catch (error: Exception) {
        RestaurantsPageResponse(page, listOf(), true)
    }

    private suspend fun getConfiguration(): ConfigurationResponse? = try {
        configRepository.getConfiguration()
    } catch (error: Exception) {
        null
    }

    private suspend fun getRemoteImage(restaurant: Restaurant,
                                       imageConfig: ImagesConfigurationResponse) {
        try {
            val localMovie = restaurantsRepository.getLocalMovie(restaurant.id)
            if (localMovie != null && restaurant.posterPath != null) {
                if (!localMovie.posterLocalPath.isNullOrEmpty()) {
                    val file = File(localMovie.posterLocalPath)
                    if(file.exists())
                        file.delete()
                }
                val imageLocalPath = restaurantsRepository.getRemoteImage(restaurant.posterPath, imageConfig)
                restaurant.posterLocalPath = imageLocalPath
                restaurantsRepository.updateMovieImagePath(restaurant.id, imageLocalPath)
            }
        }
        catch (ignored: Exception) {}
    }

}