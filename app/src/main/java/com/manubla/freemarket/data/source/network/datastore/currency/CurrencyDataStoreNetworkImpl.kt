package com.manubla.freemarket.data.source.network.datastore.currency

import android.util.Log
import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.source.network.service.CurrencyService

class CurrencyDataStoreNetworkImpl(
    private val currencyService: CurrencyService
): CurrencyDataStoreNetwork {

    override suspend fun fetchCurrencies(): List<Currency> {
        return try {
            currencyService.fetchCurrencies()
        } catch (exception: Exception) {
            Log.e(TAG_FETCH_CURRENCIES, Log.getStackTraceString(exception))
            emptyList()
        }
    }

    companion object {
        private const val TAG_FETCH_CURRENCIES= "fetchCurrencies"
    }
}