package com.manubla.freemarket.data.source.storage.datastore.remotekey

import android.util.Log
import com.manubla.freemarket.data.source.storage.dao.RemoteKeyDao
import com.manubla.freemarket.data.source.storage.entity.RemoteKey

class RemoteKeyDataStoreDatabaseImpl(
    private val remoteKeyDao: RemoteKeyDao
) : RemoteKeyDataStoreDatabase {

    override suspend fun getKeyById(id: String): RemoteKey? {
        return try {
            remoteKeyDao.getById(id)
        } catch (exception: Exception) {
            Log.e(TAG_GET_KEY_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override suspend fun storeKeys(remoteKeys: List<RemoteKey>) {
        try {
            remoteKeyDao.insertAll(remoteKeys)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_KEYS, Log.getStackTraceString(exception))
        }
    }

    override suspend fun clearStorage() {
        try {
            remoteKeyDao.deleteAll()
        } catch (exception: Exception) {
            Log.e(TAG_CLEAR_STORAGE, Log.getStackTraceString(exception))
        }
    }

    companion object {
        private const val TAG_GET_KEY_BY_ID = "getKeyById"
        private const val TAG_STORE_KEYS = "storeKeys"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}