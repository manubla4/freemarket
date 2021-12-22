package com.manubla.freemarket.data.source.storage.datastore

import com.manubla.freemarket.data.source.storage.dao.StateDao
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabaseImpl
import com.manubla.freemarket.mock.getMockState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class StateDataStoreDatabaseTest {

    private val dao = mock(StateDao::class.java)
    private val datastore: StateDataStoreDatabase = StateDataStoreDatabaseImpl(dao)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch state`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenReturn(
                getMockState()
            )
            val actual = datastore.getStateById(anyString())
            verify(dao).getById(anyString())
            assertEquals(getMockState(), actual)
        }
    }

    @Test
    fun `validate fetch state with error`() {
        runBlocking {
            `when`(
                dao.getById(anyString())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.getStateById(anyString())
            verify(dao).getById(anyString())
            assertEquals(null, actual)
        }
    }

    @Test
    fun `validate store state`() {
        runBlocking {
            val state = getMockState()
            datastore.storeState(state)
            verify(dao).insert(state)
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