package com.manubla.cinemapp.data.repository.movies

import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.response.GenreResponse
import com.manubla.cinemapp.data.service.response.ImagesConfigurationResponse
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import org.threeten.bp.ZonedDateTime

interface MoviesSourceRepository {
    suspend fun getMoviesPage(page: Int): MoviesPageResponse
    suspend fun getMoviesPage(page: Int, ratingMin: Double, ratingMax: Double): MoviesPageResponse
    suspend fun searchMoviesPage(query: String, page: Int): MoviesPageResponse
    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun getRemoteImage(posterPath: String?, imageConfig: ImagesConfigurationResponse): String
    suspend fun getLocalMovie(movieId: Int): Movie?
    suspend fun getLocalFavoriteDate(movieId: Int): ZonedDateTime?
    suspend fun getLocalGenresForMovie(movieId: Int): GenreResponse
    suspend fun storeMovies(movies: List<Movie>)
    suspend fun storeMovieGenres(movie: Movie)
    suspend fun updateMovieImagePath(movieId: Int, path: String)
    suspend fun updateMovieFavorite(movieId: Int, favoriteDate: ZonedDateTime?)
}