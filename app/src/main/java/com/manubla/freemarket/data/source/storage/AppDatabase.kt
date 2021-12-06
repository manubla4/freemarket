package com.manubla.freemarket.data.source.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao
import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.dao.StateDao
import com.manubla.freemarket.data.source.storage.dao.UserDao

@Database(entities = [Product::class, User::class, State::class, Currency::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductDao
    abstract fun usersDao(): UserDao
    abstract fun statesDao(): StateDao
    abstract fun currenciesDao(): CurrencyDao

    companion object {
        internal const val DATABASE_NAME = "freemarketdb"
    }
}