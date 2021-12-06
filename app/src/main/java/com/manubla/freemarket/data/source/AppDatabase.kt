package com.manubla.freemarket.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manubla.freemarket.data.dao.ProductDao
import com.manubla.freemarket.data.model.Product

@Database(entities = [Product::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductDao

    companion object {
        internal const val DATABASE_NAME = "freemarketdb"
    }
}