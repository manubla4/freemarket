package com.manubla.freemarket.data.source.storage.datastore.user

import com.manubla.freemarket.data.model.business.User

interface UserDataStoreDatabase {
    suspend fun getUserById(id: Long): User?
    suspend fun storeUser(user: User)
    suspend fun clearStorage()
}