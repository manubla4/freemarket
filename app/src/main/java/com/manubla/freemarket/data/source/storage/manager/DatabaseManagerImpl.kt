package com.manubla.freemarket.data.source.storage.manager

import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase

class DatabaseManagerImpl(
    private val products: ProductDataStoreDatabase,
    private val users: UserDataStoreDatabase,
    private val states: StateDataStoreDatabase
): DatabaseManager {

    override suspend fun clearAllTables() {
        products.clearStorage()
        users.clearStorage()
        states.clearStorage()
    }
}