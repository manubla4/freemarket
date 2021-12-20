package com.manubla.freemarket.data.source.storage.datastore.remotekey

import com.manubla.freemarket.data.source.storage.entity.RemoteKey

interface RemoteKeyDataStoreDatabase {
    suspend fun getKeyById(id: String): RemoteKey?
    suspend fun storeKeys(remoteKeys: List<RemoteKey>)
    suspend fun clearStorage()
}