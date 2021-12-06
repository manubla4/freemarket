package com.manubla.freemarket.data.source.network.datastore.user

import com.manubla.freemarket.data.model.User

interface UserDataStoreNetwork {
    suspend fun fetchUserById(id: Long): User?
}