package com.manubla.freemarket.data.source.storage.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manubla.freemarket.data.source.storage.database.AppDatabase
import com.manubla.freemarket.mock.getMockUser
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
class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        stopKoin()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `validate insert and fetch user`() {
        runBlocking {
            val user = getMockUser()
            userDao.insert(user)
            val actual = userDao.getById(user.id)
            assertThat(actual, equalTo(user))
        }
    }

    @Test
    @Throws(Exception::class)
    fun `validate delete users`() {
        runBlocking {
            val users = listOf(getMockUser(), getMockUser())

            users.forEach {
                userDao.insert(it)
                val actual = userDao.getById(it.id)
                assertThat(actual, equalTo(it))
            }

            userDao.deleteAll()
            users.forEach {
                val actual = userDao.getById(it.id)
                assertThat(actual, equalTo(null))
            }
        }
    }
}