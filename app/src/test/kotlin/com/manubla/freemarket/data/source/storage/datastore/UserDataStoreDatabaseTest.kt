package com.manubla.freemarket.data.source.storage.datastore

import com.manubla.freemarket.data.source.storage.dao.UserDao
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabaseImpl
import com.manubla.freemarket.mock.getMockUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class UserDataStoreDatabaseTest {

    private val dao = mock(UserDao::class.java)
    private val datastore: UserDataStoreDatabase = UserDataStoreDatabaseImpl(dao)

    @Test
    fun `validate fetch user`() {
        runBlocking {
            `when`(
                dao.getById(anyLong())
            ).thenReturn(
                getMockUser()
            )
            val actual = datastore.getUserById(anyLong())
            verify(dao).getById(anyLong())
            assertEquals(getMockUser(), actual)
        }
    }

    @Test
    fun `validate fetch user with error`() {
        runBlocking {
            `when`(
                dao.getById(anyLong())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.getUserById(anyLong())
            verify(dao).getById(anyLong())
            assertEquals(null, actual)
        }
    }

    @Test
    fun `validate store user`() {
        runBlocking {
            val user = getMockUser()
            datastore.storeUser(user)
            verify(dao).insert(user)
        }
    }

    @Test
    fun `validate clear storage`() {
        runBlocking {
            datastore.clearStorage()
            verify(dao).deleteAll()
        }
    }

}