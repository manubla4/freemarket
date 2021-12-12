package com.manubla.freemarket.data.repository.user

import com.manubla.freemarket.data.model.business.User
import com.manubla.freemarket.data.source.network.datastore.user.UserDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.user.UserDataStoreDatabase

class UserSourceRepositoryImpl(
    private val database: UserDataStoreDatabase,
    private val network: UserDataStoreNetwork
) : UserSourceRepository {

    override suspend fun fetchUser(id: Long): User? {
        val user = network.fetchUserById(id)
        user?.let {
            database.storeUser(it)
        }
        return database.getUserById(id)
    }

}