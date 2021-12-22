package com.manubla.freemarket.data.repository

import com.manubla.freemarket.data.repository.user.UserSourceRepository
import com.manubla.freemarket.data.repository.user.UserSourceRepositoryImpl
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase
import com.manubla.freemarket.mock.getMockUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class UserSourceRepositoryTest {

    private val database = mock(UserDataStoreDatabase::class.java)
    private val network = mock(UserDataStoreNetwork::class.java)
    private val repository: UserSourceRepository = UserSourceRepositoryImpl(database, network)

    @Test
    fun `validate fetch user with network and database`() {
        runBlocking {
            `when`(
                network.fetchUserById(anyLong())
            ).thenReturn(
                getMockUser()
            )
            `when`(
                database.getUserById(anyLong())
            ).thenReturn(
                getMockUser()
            )
            val actual = repository.fetchUser(anyLong())
            verify(network).fetchUserById(anyLong())
            verify(database).storeUser(getMockUser())
            verify(database).getUserById(anyLong())
            assertEquals(getMockUser(), actual)
        }
    }

    @Test
    fun `validate fetch user without network but with database`() {
        runBlocking {
            `when`(
                network.fetchUserById(anyLong())
            ).thenReturn(
               null
            )
            `when`(
                database.getUserById(anyLong())
            ).thenReturn(
                getMockUser()
            )
            val actual = repository.fetchUser(anyLong())
            verify(network).fetchUserById(anyLong())
            verify(database, times(0)).storeUser(getMockUser())
            verify(database).getUserById(anyLong())
            assertEquals(getMockUser(), actual)
        }
    }

    @Test
    fun `validate fetch user without network or database`() {
        runBlocking {
            `when`(
                network.fetchUserById(anyLong())
            ).thenReturn(
                null
            )
            `when`(
                database.getUserById(anyLong())
            ).thenReturn(
                null
            )
            val actual = repository.fetchUser(anyLong())
            verify(network).fetchUserById(anyLong())
            verify(database, times(0)).storeUser(getMockUser())
            verify(database).getUserById(anyLong())
            assertEquals(null, actual)
        }
    }

}