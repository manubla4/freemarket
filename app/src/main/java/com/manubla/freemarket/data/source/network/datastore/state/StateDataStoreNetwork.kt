package com.manubla.freemarket.data.source.network.datastore.state

import com.manubla.freemarket.data.model.business.State

interface StateDataStoreNetwork {
    suspend fun fetchStateById(id: String): State?
}