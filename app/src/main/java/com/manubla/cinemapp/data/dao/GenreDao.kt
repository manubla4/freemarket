package com.manubla.cinemapp.data.dao

import androidx.room.*
import com.manubla.cinemapp.data.model.Genre

@Dao
interface GenreDao {

    @Query("SELECT * FROM ${Genre.TABLE_NAME}")
    suspend fun getAll(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<Genre>)

    @Delete
    suspend fun delete(genre: Genre)

}