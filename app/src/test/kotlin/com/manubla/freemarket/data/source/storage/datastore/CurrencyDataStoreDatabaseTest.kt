package com.manubla.freemarket.data.source.storage.datastore

import com.manubla.freemarket.data.source.storage.dao.CurrencyDao
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabaseImpl
import com.manubla.freemarket.mock.getMockCurrencies
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CurrencyDataStoreDatabaseTest {

    private val dao = mock(CurrencyDao::class.java)
    private val datastore: CurrencyDataStoreDatabase = CurrencyDataStoreDatabaseImpl(dao)

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun `validate store currencies`() {
        runBlocking {
            val currencies = getMockCurrencies()
            datastore.storeCurrencies(currencies)
            verify(dao).insertAll(currencies)
        }
    }

}