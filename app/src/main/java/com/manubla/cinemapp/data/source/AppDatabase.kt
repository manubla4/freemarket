package com.manubla.cinemapp.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manubla.cinemapp.data.dao.GenreDao
import com.manubla.cinemapp.data.dao.MovieDao
import com.manubla.cinemapp.data.dao.ReviewDao
import com.manubla.cinemapp.data.helper.converter.Converters
import com.manubla.cinemapp.data.model.Genre
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.model.MovieGenre
import com.manubla.cinemapp.data.model.Review

@Database(entities = [Movie::class, Review::class, Genre::class, MovieGenre::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun reviewDao(): ReviewDao
    abstract fun genreDao(): GenreDao
}