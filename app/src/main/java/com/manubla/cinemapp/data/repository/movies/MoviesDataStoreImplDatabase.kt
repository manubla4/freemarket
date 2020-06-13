package com.manubla.cinemapp.data.repository.movies

import com.manubla.cinemapp.data.dao.MovieDao
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.model.MovieGenre
import com.manubla.cinemapp.data.service.response.GenreResponse
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import org.threeten.bp.ZonedDateTime

class MoviesDataStoreImplDatabase(private val movieDao: MovieDao) : MoviesDataStore {
    private val pageRows = 20

    override suspend fun getMoviesPage(page: Int): MoviesPageResponse {
        val limit = (page - 1) * pageRows
        val results = movieDao.getAllWithLimit(limit, pageRows)
        return MoviesPageResponse(page, results, false)
    }

    override suspend fun getMoviesPage(
        page: Int,
        ratingMin: Double,
        ratingMax: Double
    ): MoviesPageResponse {
        val results = movieDao.getAllByRating(ratingMin, ratingMax)
        return MoviesPageResponse(page, results, false)
    }

    override suspend fun getMovie(movieId: Int): Movie? {
        return movieDao.get(movieId)
    }

    suspend fun getGenresForMovie(movieId: Int): GenreResponse {
        return GenreResponse(movieDao.getGenresForMovie(movieId))
    }

    suspend fun storeMovies(movies: List<Movie>) {
        movieDao.insertAll(movies)
    }

    suspend fun storeMovieGenre(movieGenre: MovieGenre) {
        movieDao.insertMovieGenre(movieGenre)
    }

    suspend fun updateMovieImagePath(movieId: Int, path: String) {
        movieDao.updateMovieImagePath(movieId, path)
    }

    suspend fun updateMovieFavoriteDate(movieId: Int, favoriteDate: ZonedDateTime?) {
        movieDao.updateMovieFavoriteDate(movieId, favoriteDate)
    }

    suspend fun getFavoriteDate(movieId: Int): ZonedDateTime? {
        return movieDao.get(movieId)?.favoriteDate
    }

    suspend fun getFavoriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies().sortedBy { it.favoriteDate }
    }
}