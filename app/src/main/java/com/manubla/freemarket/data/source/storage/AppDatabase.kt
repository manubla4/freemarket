package com.manubla.freemarket.data.source.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.model.business.User
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao
import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.dao.StateDao
import com.manubla.freemarket.data.source.storage.dao.UserDao

@Database(entities = [Product::class, User::class, State::class, Currency::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun stateDao(): StateDao
    abstract fun currencyDao(): CurrencyDao

    companion object {
        internal const val DATABASE_NAME = "freemarketdb"
    }
}