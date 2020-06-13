package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.service.response.GenreResponse
import com.manubla.restoya.data.service.response.ImagesConfigurationResponse
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import org.threeten.bp.ZonedDateTime

interface RestaurantsSourceRepository {
    suspend fun getMoviesPage(page: Int): RestaurantsPageResponse
    suspend fun getMoviesPage(page: Int, ratingMin: Double, ratingMax: Double): RestaurantsPageResponse
    suspend fun searchMoviesPage(query: String, page: Int): RestaurantsPageResponse
    suspend fun getFavoriteMovies(): List<Restaurant>
    suspend fun getRemoteImage(posterPath: String?, imageConfig: ImagesConfigurationResponse): String
    suspend fun getLocalMovie(movieId: Int): Restaurant?
    suspend fun getLocalFavoriteDate(movieId: Int): ZonedDateTime?
    suspend fun getLocalGenresForMovie(movieId: Int): GenreResponse
    suspend fun storeMovies(restaurants: List<Restaurant>)
    suspend fun storeMovieGenres(restaurant: Restaurant)
    suspend fun updateMovieImagePath(movieId: Int, path: String)
    suspend fun updateMovieFavorite(movieId: Int, favoriteDate: ZonedDateTime?)
}