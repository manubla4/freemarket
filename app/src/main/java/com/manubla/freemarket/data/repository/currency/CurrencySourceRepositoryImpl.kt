package com.manubla.freemarket.data.repository.currency

import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.source.network.datastore.currency.CurrencyDataStoreNetwork
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.currency.CurrencyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase

class CurrencySourceRepositoryImpl(
    private val database: CurrencyDataStoreDatabase,
    private val network: CurrencyDataStoreNetwork
) : CurrencySourceRepository {

    override suspend fun fetchCurrency(id: String): Currency? {
        TODO("Not yet implemented")
    }

}