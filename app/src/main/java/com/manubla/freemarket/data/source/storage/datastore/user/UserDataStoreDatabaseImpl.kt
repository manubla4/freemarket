package com.manubla.freemarket.data.source.storage.datastore.user

import android.util.Log
import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.source.storage.dao.UserDao

class UserDataStoreDatabaseImpl(
    private val userDao: UserDao
) : UserDataStoreDatabase {

    override suspend fun getUserById(id: Long): User? {
        return try {
            userDao.getById(id)
        } catch (exception: Exception) {
            Log.e(TAG_GET_USER_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override suspend fun storeUser(user: User) {
        try {
            userDao.insert(user)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_USER, Log.getStackTraceString(exception))
        }
    }

    override suspend fun clearStorage() {
        try {
            userDao.deleteAll()
        } catch (exception: Exception) {
            Log.e(TAG_CLEAR_STORAGE, Log.getStackTraceString(exception))
        }
    }


    companion object {
        private const val TAG_GET_USER_BY_ID = "getUserById"
        private const val TAG_STORE_USER = "storeUser"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}