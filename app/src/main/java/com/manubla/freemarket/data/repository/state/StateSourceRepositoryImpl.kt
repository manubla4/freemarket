package com.manubla.freemarket.data.repository.state

import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.source.network.datastore.state.StateDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.state.StateDataStoreDatabase

class StateSourceRepositoryImpl(
    private val database: StateDataStoreDatabase,
    private val network: StateDataStoreNetwork
) : StateSourceRepository {

    override suspend fun fetchState(id: String): State? {
        TODO("Not yet implemented")
    }

}