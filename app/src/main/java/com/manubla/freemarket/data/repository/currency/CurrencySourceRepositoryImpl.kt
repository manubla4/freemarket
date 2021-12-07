package com.manubla.freemarket.data.repository.currency

import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabase

class CurrencySourceRepositoryImpl(
    private val database: CurrencyDataStoreDatabase,
    private val network: CurrencyDataStoreNetwork
) : CurrencySourceRepository {

    override suspend fun fetchCurrencies() {
        val currencies = network.fetchCurrencies()
        database.storeCurrencies(currencies)
    }

}