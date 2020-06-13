package com.manubla.restoya.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.manubla.restoya.data.model.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM ${Restaurant.TABLE_NAME}")
    suspend fun getAll(): List<Restaurant>

//    @Query("SELECT * FROM ${Restaurant.TABLE_NAME} WHERE vote_average >= :ratingMin AND vote_average <= :ratingMax")
//    suspend fun getAllByRating(ratingMin: Double, ratingMax: Double): List<Restaurant>
//
//    @Query("SELECT * FROM ${Restaurant.TABLE_NAME} LIMIT :limit, :rows")
//    suspend fun getAllWithLimit(limit: Int, rows: Int): List<Restaurant>
//
//    @Query("SELECT * FROM ${Restaurant.TABLE_NAME} WHERE favorite_date IS NOT NULL")
//    suspend fun getFavoriteMovies(): List<Restaurant>
//
//    @Query("SELECT * FROM ${Restaurant.TABLE_NAME} WHERE id=:movieId")
//    suspend fun get(movieId: Int): Restaurant?
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertAll(restaurants: List<Restaurant>)
//
//    @Delete
//    suspend fun delete(restaurant: Restaurant)
//
//    @Query("UPDATE ${Restaurant.TABLE_NAME} SET poster_local_path=:path WHERE id =:movieId")
//    suspend fun updateMovieImagePath(movieId: Int, path: String)
//
//    @Query("UPDATE ${Restaurant.TABLE_NAME} SET favorite_date=:favoriteDate WHERE id =:movieId")
//    suspend fun updateMovieFavoriteDate(movieId: Int, favoriteDate: ZonedDateTime?)
//
//    @Query("SELECT * FROM ${Genre.TABLE_NAME} INNER JOIN ${MovieGenre.TABLE_NAME} ON " +
//            "${Genre.TABLE_NAME}.id=${MovieGenre.TABLE_NAME}.genre_id WHERE ${MovieGenre.TABLE_NAME}.movie_id=:movieId")
//    suspend fun getGenresForMovie(movieId: Int): List<Genre>
//
//    @Query("SELECT * FROM ${Restaurant.TABLE_NAME} INNER JOIN ${MovieGenre.TABLE_NAME} ON " +
//            "${Restaurant.TABLE_NAME}.id=${MovieGenre.TABLE_NAME}.movie_id WHERE ${MovieGenre.TABLE_NAME}.genre_id=:genreId")
//    suspend fun getMoviesForGenre(genreId: Int): List<Restaurant>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovieGenre(movieGenre: MovieGenre)

}