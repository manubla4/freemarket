package com.manubla.freemarket.data.repository

import com.manubla.freemarket.data.repository.currency.CurrencySourceRepository
import com.manubla.freemarket.data.repository.currency.CurrencySourceRepositoryImpl
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabase
import com.manubla.freemarket.mock.getMockCurrencies
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CurrencySourceRepositoryTest {

    private val database = mock(CurrencyDataStoreDatabase::class.java)
    private val network = mock(CurrencyDataStoreNetwork::class.java)
    private val repository: CurrencySourceRepository = CurrencySourceRepositoryImpl(database, network)

    @Before
    fun setup() {
        runBlocking {
            Mockito.`when`(
                network.fetchCurrencies()
            ).thenReturn(
                getMockCurrencies()
            )
        }
    }

    @Test
    fun `validate fetch currencies`() {
        runBlocking {
            repository.fetchCurrencies()
            verify(network).fetchCurrencies()
            verify(database).storeCurrencies(getMockCurrencies())
        }
    }

}