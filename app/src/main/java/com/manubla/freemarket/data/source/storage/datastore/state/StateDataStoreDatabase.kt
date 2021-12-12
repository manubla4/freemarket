package com.manubla.freemarket.data.source.storage.datastore.state

import com.manubla.freemarket.data.model.business.State

interface StateDataStoreDatabase {
    suspend fun getStateById(id: String): State?
    suspend fun storeState(state: State)
    suspend fun clearStorage()
}