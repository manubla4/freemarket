package com.manubla.freemarket.data.repository.user

import com.manubla.freemarket.data.model.business.User

interface UserSourceRepository {
    suspend fun fetchUser(id: Long): User?
}