package com.manubla.freemarket.data.source.storage.datastore.currency

import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.source.storage.dao.CurrencyDao

class CurrencyDataStoreDatabaseImpl(
    private val currencyDao: CurrencyDao
) : CurrencyDataStoreDatabase {

    override suspend fun getCurrencyById(id: String): Currency? {
        TODO("Not yet implemented")
    }

    override suspend fun storeCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }

    override suspend fun clearStorage() {
        TODO("Not yet implemented")
    }

//    override suspend fun getProductById(id: String): Product? {
//        return try {
//            productDao.getById(id)
//        } catch (exception: Exception) {
//            Log.e(TAG_GET_PRODUCT_BY_ID, Log.getStackTraceString(exception))
//            null
//        }
//    }


    companion object {
        private const val TAG_GET_CURRENCY_BY_ID = "getCurrencyById"
        private const val TAG_STORE_CURRENCY = "storeCurrency"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}