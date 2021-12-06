package com.manubla.freemarket.data.source.storage.datastore.state

import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.source.storage.dao.StateDao

class StateDataStoreDatabaseImpl(
    private val stateDao: StateDao
) : StateDataStoreDatabase {

    override suspend fun getStateById(id: String): State? {
        TODO("Not yet implemented")
    }

    override suspend fun storeState(state: State) {
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
        private const val TAG_GET_STATE_BY_ID = "getStateById"
        private const val TAG_STORE_STATE = "storeState"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}