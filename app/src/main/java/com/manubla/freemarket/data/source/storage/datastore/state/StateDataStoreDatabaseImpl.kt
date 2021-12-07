package com.manubla.freemarket.data.source.storage.datastore.state

import android.util.Log
import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.source.storage.dao.StateDao

class StateDataStoreDatabaseImpl(
    private val stateDao: StateDao
) : StateDataStoreDatabase {

    override suspend fun getStateById(id: String): State? {
        return try {
            stateDao.getById(id)
        } catch (exception: Exception) {
            Log.e(TAG_GET_STATE_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override suspend fun storeState(state: State) {
        try {
            stateDao.insert(state)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_STATE, Log.getStackTraceString(exception))
        }
    }

    override suspend fun clearStorage() {
        try {
            stateDao.deleteAll()
        } catch (exception: Exception) {
            Log.e(TAG_CLEAR_STORAGE, Log.getStackTraceString(exception))
        }
    }

    companion object {
        private const val TAG_GET_STATE_BY_ID = "getStateById"
        private const val TAG_STORE_STATE = "storeState"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}