package com.manubla.freemarket.data.repository

import com.manubla.freemarket.data.repository.state.StateSourceRepository
import com.manubla.freemarket.data.repository.state.StateSourceRepositoryImpl
import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.mock.getMockState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class StateSourceRepositoryTest {

    private val database = mock(StateDataStoreDatabase::class.java)
    private val network = mock(StateDataStoreNetwork::class.java)
    private val repository: StateSourceRepository = StateSourceRepositoryImpl(database, network)


    @Test
    fun `validate fetch state with network and database`() {
        runBlocking {
            Mockito.`when`(
                network.fetchStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                getMockState()
            )
            Mockito.`when`(
                database.getStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                getMockState()
            )
            val id = ArgumentMatchers.anyString()
            val actual = repository.fetchState(id)
            verify(network).fetchStateById(id)
            verify(database).storeState(getMockState())
            verify(database, Mockito.times(0)).getStateById(id)
            assertEquals(getMockState(), actual)
        }
    }


    @Test
    fun `validate fetch state without network but with database`() {
        runBlocking {
            Mockito.`when`(
                network.fetchStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                null
            )
            Mockito.`when`(
                database.getStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                getMockState()
            )
            val id = ArgumentMatchers.anyString()
            val actual = repository.fetchState(id)
            verify(network).fetchStateById(id)
            verify(database, Mockito.times(0)).storeState(getMockState())
            verify(database).getStateById(id)
            assertEquals(getMockState(), actual)
        }
    }

    @Test
    fun `validate fetch state without network or database`() {
        runBlocking {
            Mockito.`when`(
                network.fetchStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                null
            )
            Mockito.`when`(
                database.getStateById(ArgumentMatchers.anyString())
            ).thenReturn(
                null
            )
            val id = ArgumentMatchers.anyString()
            val actual = repository.fetchState(id)
            verify(network).fetchStateById(id)
            verify(database, Mockito.times(0)).storeState(getMockState())
            verify(database).getStateById(id)
            assertEquals(null, actual)
        }
    }

}