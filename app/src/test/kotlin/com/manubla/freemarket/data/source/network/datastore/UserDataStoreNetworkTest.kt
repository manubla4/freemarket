package com.manubla.freemarket.data.source.network.datastore

import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.UserService
import com.manubla.freemarket.mock.getMockUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class UserDataStoreNetworkTest {

    private val service = mock(UserService::class.java)
    private val datastore: UserDataStoreNetwork = UserDataStoreNetworkImpl(service)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch user with network`() {
        runBlocking {
            `when`(
                service.fetchUser(anyLong())
            ).thenReturn(
                getMockUser()
            )
            val actual = datastore.fetchUserById(anyLong())
            verify(service).fetchUser(anyLong())
            assertEquals(getMockUser(), actual)
        }
    }

    @Test
    fun `validate fetch user without network`() {
        runBlocking {
            `when`(
                service.fetchUser(anyLong())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.fetchUserById(anyLong())
            verify(service).fetchUser(anyLong())
            assertEquals(null, actual)
        }
    }
}