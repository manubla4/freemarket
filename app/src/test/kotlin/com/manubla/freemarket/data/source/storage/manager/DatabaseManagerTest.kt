package com.manubla.freemarket.data.source.storage.manager

import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DatabaseManagerTest {

    private val keys = mock(RemoteKeyDataStoreDatabase::class.java)
    private val products = mock(ProductDataStoreDatabase::class.java)
    private val users = mock(UserDataStoreDatabase::class.java)
    private val states = mock(StateDataStoreDatabase::class.java)
    private val datastore: DatabaseManager = DatabaseManagerImpl(keys, products, users, states)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate clear all tables`() {
        runBlocking {
            datastore.clearAllTables()
            verify(keys).clearStorage()
            verify(products).clearStorage()
            verify(users).clearStorage()
            verify(states).clearStorage()
        }
    }

}