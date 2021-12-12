package com.manubla.freemarket.data.source.network.datastore.state

import android.util.Log
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.source.network.service.StateService

class StateDataStoreNetworkImpl(
    private val stateService: StateService
): StateDataStoreNetwork {

    override suspend fun fetchStateById(id: String): State? {
        return try {
            stateService.fetchState(id)
        } catch (exception: Exception) {
            Log.e(TAG_FETCH_STATE, Log.getStackTraceString(exception))
            null
        }
    }

    companion object {
        private const val TAG_FETCH_STATE = "fetchStateById"
    }
}