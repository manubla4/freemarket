package com.manubla.freemarket.data.source.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.mock.getMockCurrencies
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CurrencyDaoTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        currencyDao = db.currencyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `validate insert and fetch currencies`() {
        runBlocking {
            val currencies = getMockCurrencies()
            currencyDao.insertAll(currencies)
            currencies.forEach {
                val actual = currencyDao.getById(it.id)
                assertThat(actual, equalTo(it))
            }
        }
    }

}