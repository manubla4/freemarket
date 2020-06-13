package com.manubla.cinemapp.data.dao

import androidx.room.*
import com.manubla.cinemapp.data.model.Genre
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.model.MovieGenre
import org.threeten.bp.ZonedDateTime

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Movie.TABLE_NAME}")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE vote_average >= :ratingMin AND vote_average <= :ratingMax")
    suspend fun getAllByRating(ratingMin: Double, ratingMax: Double): List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} LIMIT :limit, :rows")
    suspend fun getAllWithLimit(limit: Int, rows: Int): List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE favorite_date IS NOT NULL")
    suspend fun getFavoriteMovies(): List<Movie>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} WHERE id=:movieId")
    suspend fun get(movieId: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("UPDATE ${Movie.TABLE_NAME} SET poster_local_path=:path WHERE id =:movieId")
    suspend fun updateMovieImagePath(movieId: Int, path: String)

    @Query("UPDATE ${Movie.TABLE_NAME} SET favorite_date=:favoriteDate WHERE id =:movieId")
    suspend fun updateMovieFavoriteDate(movieId: Int, favoriteDate: ZonedDateTime?)

    @Query("SELECT * FROM ${Genre.TABLE_NAME} INNER JOIN ${MovieGenre.TABLE_NAME} ON " +
            "${Genre.TABLE_NAME}.id=${MovieGenre.TABLE_NAME}.genre_id WHERE ${MovieGenre.TABLE_NAME}.movie_id=:movieId")
    suspend fun getGenresForMovie(movieId: Int): List<Genre>

    @Query("SELECT * FROM ${Movie.TABLE_NAME} INNER JOIN ${MovieGenre.TABLE_NAME} ON " +
            "${Movie.TABLE_NAME}.id=${MovieGenre.TABLE_NAME}.movie_id WHERE ${MovieGenre.TABLE_NAME}.genre_id=:genreId")
    suspend fun getMoviesForGenre(genreId: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenre(movieGenre: MovieGenre)

}