package com.manubla.freemarket.data.repository.state

import com.manubla.freemarket.data.model.State

interface StateSourceRepository {
    suspend fun fetchState(id: String): State?
}