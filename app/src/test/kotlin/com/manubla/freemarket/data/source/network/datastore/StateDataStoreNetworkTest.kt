package com.manubla.freemarket.data.source.network.datastore

import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.StateService
import com.manubla.freemarket.mock.getMockState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class StateDataStoreNetworkTest {

    private val service = mock(StateService::class.java)
    private val datastore: StateDataStoreNetwork = StateDataStoreNetworkImpl(service)

    @Test
    fun `validate fetch state with network`() {
        runBlocking {
            `when`(
                service.fetchState(anyString())
            ).thenReturn(
                getMockState()
            )
            val actual = datastore.fetchStateById(anyString())
            verify(service).fetchState(anyString())
            assertEquals(getMockState(), actual)
        }
    }

    @Test
    fun `validate fetch state without network`() {
        runBlocking {
            `when`(
                service.fetchState(anyString())
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.fetchStateById(anyString())
            verify(service).fetchState(anyString())
            assertEquals(null, actual)
        }
    }
}