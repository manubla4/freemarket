package com.manubla.freemarket.data.source.network.datastore

import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetworkImpl
import com.manubla.freemarket.data.source.network.service.CurrencyService
import com.manubla.freemarket.mock.getMockCurrencies
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.*

class CurrencyDataStoreNetworkTest {

    private val service = mock(CurrencyService::class.java)
    private val datastore: CurrencyDataStoreNetwork = CurrencyDataStoreNetworkImpl(service)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate fetch currency with network`() {
        runBlocking {
            `when`(
                service.fetchCurrencies()
            ).thenReturn(
                getMockCurrencies()
            )
            val actual = datastore.fetchCurrencies()
            verify(service).fetchCurrencies()
            assertEquals(getMockCurrencies(), actual)
        }
    }

    @Test
    fun `validate fetch currency without network`() {
        runBlocking {
            `when`(
                service.fetchCurrencies()
            ).thenThrow(
                RuntimeException()
            )
            val actual = datastore.fetchCurrencies()
            verify(service).fetchCurrencies()
            assertEquals(emptyList<Currency>(), actual)
        }
    }
}