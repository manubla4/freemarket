package com.manubla.freemarket.data.source.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.mock.getMockState
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
class StateDaoTest {

    private lateinit var stateDao: StateDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        stateDao = db.stateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `validate insert and fetch state`() {
        runBlocking {
            val state = getMockState()
            stateDao.insert(state)
            val actual = stateDao.getById(state.id)
            assertThat(actual, equalTo(state))
        }
    }

    @Test
    @Throws(Exception::class)
    fun `validate delete states`() {
        runBlocking {
            val states = listOf(getMockState(), getMockState())

            states.forEach {
                stateDao.insert(it)
                val actual = stateDao.getById(it.id)
                assertThat(actual, equalTo(it))
            }

            stateDao.deleteAll()
            states.forEach {
                val actual = stateDao.getById(it.id)
                assertThat(actual, equalTo(null))
            }
        }
    }
}