package com.manubla.freemarket.data.source.storage.manager

import android.util.Log
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase

class DatabaseManagerImpl(
    private val keys: RemoteKeyDataStoreDatabase,
    private val products: ProductDataStoreDatabase,
    private val users: UserDataStoreDatabase,
    private val states: StateDataStoreDatabase
): DatabaseManager {

    override suspend fun clearAllTables() {
        try {
            keys.clearStorage()
            products.clearStorage()
            users.clearStorage()
            states.clearStorage()
        } catch (exception: Exception) {
            Log.e(TAG_CLEAR_ALL_TABLES, Log.getStackTraceString(exception))
        }
    }

    companion object {
        private const val TAG_CLEAR_ALL_TABLES = "clearAllTables"
    }
}