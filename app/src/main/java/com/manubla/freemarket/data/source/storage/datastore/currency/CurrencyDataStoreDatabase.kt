package com.manubla.freemarket.data.source.storage.datastore.currency

import com.manubla.freemarket.data.model.Currency

interface CurrencyDataStoreDatabase {
    suspend fun storeCurrencies(currencies: List<Currency>)
}