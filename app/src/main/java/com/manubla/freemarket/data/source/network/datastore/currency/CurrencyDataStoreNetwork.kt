package com.manubla.freemarket.data.source.network.datastore.currency

import com.manubla.freemarket.data.model.business.Currency

interface CurrencyDataStoreNetwork {
    suspend fun fetchCurrencies(): List<Currency>
}