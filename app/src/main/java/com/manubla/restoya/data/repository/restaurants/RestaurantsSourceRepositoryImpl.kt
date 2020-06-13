package com.manubla.restoya.data.repository.restaurants

import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.model.MovieGenre
import com.manubla.restoya.data.service.response.GenreResponse
import com.manubla.restoya.data.service.response.ImagesConfigurationResponse
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import org.threeten.bp.ZonedDateTime

class RestaurantsSourceRepositoryImpl(var factory: RestaurantsDataStoreFactory) : RestaurantsSourceRepository {

    override suspend fun searchMoviesPage(query: String, page: Int): RestaurantsPageResponse {
        return factory.moviesDataStoreCloud.searchMoviesPage(query, page)
    }

    override suspend fun getFavoriteMovies(): List<Restaurant> {
        return factory.moviesDataStoreDatabase.getFavoriteMovies()
    }

    override suspend fun getLocalMovie(movieId: Int): Restaurant? {
        return factory.moviesDataStoreDatabase.getMovie(movieId)
    }

    override suspend fun getMoviesPage(page: Int): RestaurantsPageResponse {
        return factory.restaurantsDataStoreFactory.getMoviesPage(page)
    }

    override suspend fun getMoviesPage(
        page: Int,
        ratingMin: Double,
        ratingMax: Double
    ): RestaurantsPageResponse {
        return factory.restaurantsDataStoreFactory.getMoviesPage(page, ratingMin, ratingMax)
    }

    override suspend fun storeMovies(restaurants: List<Restaurant>) {
        factory.moviesDataStoreDatabase.storeMovies(restaurants)
    }

    override suspend fun storeMovieGenres(restaurant: Restaurant) {
        restaurant.genreIds?.let {
            for (genreId in it) {
                val movieGenre = MovieGenre(restaurant.id, genreId)
                try {
                    factory.moviesDataStoreDatabase.storeMovieGenre(movieGenre)
                }
                catch (ignored: Exception) {
                }
            }
        }
    }

    override suspend fun getRemoteImage(posterPath: String?, imageConfig: ImagesConfigurationResponse): String {
        return factory.moviesDataStoreCloud.getRemoteImage(posterPath, imageConfig)
    }

    override suspend fun updateMovieImagePath(movieId: Int, path: String) {
        factory.moviesDataStoreDatabase.updateMovieImagePath(movieId, path)
    }

    override suspend fun updateMovieFavorite(movieId: Int, favoriteDate: ZonedDateTime?) {
        factory.moviesDataStoreDatabase.updateMovieFavoriteDate(movieId, favoriteDate)
    }

    override suspend fun getLocalGenresForMovie(movieId: Int): GenreResponse {
        return factory.moviesDataStoreDatabase.getGenresForMovie(movieId)
    }

    override suspend fun getLocalFavoriteDate(movieId: Int): ZonedDateTime? {
        return factory.moviesDataStoreDatabase.getFavoriteDate(movieId)
    }

}