package com.manubla.freemarket.data.source.network.datastore.user

import android.util.Log
import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.source.network.service.UserService

class UserDataStoreNetworkImpl(
    private val userService: UserService
): UserDataStoreNetwork {

    override suspend fun fetchUserById(id: Long): User? {
        return try {
            userService.fetchUser(id)
        } catch (exception: Exception) {
            Log.e(TAG_FETCH_USER, Log.getStackTraceString(exception))
            null
        }
    }

    companion object {
        private const val TAG_FETCH_USER = "fetchUserById"
    }

}