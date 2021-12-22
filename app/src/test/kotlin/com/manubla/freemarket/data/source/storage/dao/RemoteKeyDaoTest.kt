package com.manubla.freemarket.data.source.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.mock.getMockRemoteKeys
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
class RemoteKeyDaoTest {

    private lateinit var remoteKeyDao: RemoteKeyDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        remoteKeyDao = db.remoteKeyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `validate insert and fetch remote key`() {
        runBlocking {
            val remoteKeys = getMockRemoteKeys()
            remoteKeyDao.insertAll(remoteKeys)
            remoteKeys.forEach {
                val actual = remoteKeyDao.getById(it.id)
                assertThat(actual, equalTo(it))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun `validate delete remote keys`() {
        runBlocking {
            val remoteKeys = getMockRemoteKeys()
            remoteKeyDao.insertAll(remoteKeys)
            remoteKeys.forEach {
                val actual = remoteKeyDao.getById(it.id)
                assertThat(actual, equalTo(it))
            }

            remoteKeyDao.deleteAll()
            remoteKeys.forEach {
                val actual = remoteKeyDao.getById(it.id)
                assertThat(actual, equalTo(null))
            }
        }
    }
}