package com.manubla.freemarket.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manubla.freemarket.data.dao.RestaurantDao
import com.manubla.freemarket.data.model.Restaurant

@Database(entities = [Restaurant::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}