package com.manubla.cinemapp.data.repository.movies

import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.model.MovieGenre
import com.manubla.cinemapp.data.service.response.GenreResponse
import com.manubla.cinemapp.data.service.response.ImagesConfigurationResponse
import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import org.threeten.bp.ZonedDateTime

class MoviesSourceRepositoryImpl(var factory: MoviesDataStoreFactory) : MoviesSourceRepository {

    override suspend fun searchMoviesPage(query: String, page: Int): MoviesPageResponse {
        return factory.moviesDataStoreCloud.searchMoviesPage(query, page)
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return factory.moviesDataStoreDatabase.getFavoriteMovies()
    }

    override suspend fun getLocalMovie(movieId: Int): Movie? {
        return factory.moviesDataStoreDatabase.getMovie(movieId)
    }

    override suspend fun getMoviesPage(page: Int): MoviesPageResponse {
        return factory.moviesDataStoreFactory.getMoviesPage(page)
    }

    override suspend fun getMoviesPage(
        page: Int,
        ratingMin: Double,
        ratingMax: Double
    ): MoviesPageResponse {
        return factory.moviesDataStoreFactory.getMoviesPage(page, ratingMin, ratingMax)
    }

    override suspend fun storeMovies(movies: List<Movie>) {
        factory.moviesDataStoreDatabase.storeMovies(movies)
    }

    override suspend fun storeMovieGenres(movie: Movie) {
        movie.genreIds?.let {
            for (genreId in it) {
                val movieGenre = MovieGenre(movie.id, genreId)
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