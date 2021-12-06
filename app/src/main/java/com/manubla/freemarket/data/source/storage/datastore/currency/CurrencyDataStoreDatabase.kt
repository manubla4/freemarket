package com.manubla.freemarket.data.source.storage.datastore.currency

import com.manubla.freemarket.data.model.Currency

interface CurrencyDataStoreDatabase {
    suspend fun getCurrencyById(id: String): Currency?
    suspend fun storeCurrency(currency: Currency)
    suspend fun clearStorage()
}