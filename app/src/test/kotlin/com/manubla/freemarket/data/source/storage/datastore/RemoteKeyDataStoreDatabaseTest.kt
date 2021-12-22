package com.manubla.freemarket.data.source.storage.datastore

import com.manubla.freemarket.data.source.storage.dao.RemoteKeyDao
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabaseImpl
import com.manubla.freemarket.mock.getMockRemoteKey
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class RemoteKeyDataStoreDatabaseTest {

    private val dao = mock(RemoteKeyDao::class.java)
    private val datastore: RemoteKeyDataStoreDatabase = RemoteKeyDataStoreDatabaseImpl(dao)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch remote key`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenReturn(
                getMockRemoteKey()
            )
            val actual = datastore.getKeyById(anyString())
            verify(dao).getById(anyString())
            assertEquals(getMockRemoteKey(), actual)
        }
    }

    @Test
    fun `validate fetch remote key with error`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.getKeyById(anyString())
            verify(dao).getById(anyString())
            assertEquals(null, actual)
        }
    }

    @Test
    fun `validate store remote key`() {
        runBlocking {
            val keys = listOf(getMockRemoteKey())
            datastore.storeKeys(keys)
            verify(dao).insertAll(keys)
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