package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.dao.RestaurantDao
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.model.MovieGenre
import com.manubla.restoya.data.service.response.GenreResponse
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import org.threeten.bp.ZonedDateTime

class RestaurantsDataStoreImplDatabase(private val restaurantDao: RestaurantDao) : RestaurantsDataStore {
    private val pageRows = 20

    override suspend fun getMoviesPage(page: Int): RestaurantsPageResponse {
        val limit = (page - 1) * pageRows
        val results = restaurantDao.getAllWithLimit(limit, pageRows)
        return RestaurantsPageResponse(page, results, false)
    }

    override suspend fun getMoviesPage(
        page: Int,
        ratingMin: Double,
        ratingMax: Double
    ): RestaurantsPageResponse {
        val results = restaurantDao.getAllByRating(ratingMin, ratingMax)
        return RestaurantsPageResponse(page, results, false)
    }

    override suspend fun getMovie(movieId: Int): Restaurant? {
        return restaurantDao.get(movieId)
    }

    suspend fun getGenresForMovie(movieId: Int): GenreResponse {
        return GenreResponse(restaurantDao.getGenresForMovie(movieId))
    }

    suspend fun storeMovies(restaurants: List<Restaurant>) {
        restaurantDao.insertAll(restaurants)
    }

    suspend fun storeMovieGenre(movieGenre: MovieGenre) {
        restaurantDao.insertMovieGenre(movieGenre)
    }

    suspend fun updateMovieImagePath(movieId: Int, path: String) {
        restaurantDao.updateMovieImagePath(movieId, path)
    }

    suspend fun updateMovieFavoriteDate(movieId: Int, favoriteDate: ZonedDateTime?) {
        restaurantDao.updateMovieFavoriteDate(movieId, favoriteDate)
    }

    suspend fun getFavoriteDate(movieId: Int): ZonedDateTime? {
        return restaurantDao.get(movieId)?.favoriteDate
    }

    suspend fun getFavoriteMovies(): List<Restaurant> {
        return restaurantDao.getFavoriteMovies().sortedBy { it.favoriteDate }
    }
}