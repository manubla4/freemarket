package com.manubla.freemarket.data.source.storage.datastore.currency

import android.util.Log
import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao

class CurrencyDataStoreDatabaseImpl(
    private val currencyDao: CurrencyDao
) : CurrencyDataStoreDatabase {

    override suspend fun storeCurrencies(currencies: List<Currency>) {
        try {
            currencyDao.insertAll(currencies)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_CURRENCIES, Log.getStackTraceString(exception))
        }
    }

    companion object {
        private const val TAG_STORE_CURRENCIES = "storeCurrencies"
    }
}