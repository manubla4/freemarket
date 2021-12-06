package com.manubla.freemarket.data.source.storage.datastore.user

import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.source.storage.dao.UserDao

class UserDataStoreDatabaseImpl(
    private val userDao: UserDao
) : UserDataStoreDatabase {

    override suspend fun getUserById(id: Long): User? {
        TODO("Not yet implemented")
    }

    override suspend fun storeUser(user: User) {
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
        private const val TAG_GET_USER_BY_ID = "getUserById"
        private const val TAG_STORE_USER = "storeUser"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}