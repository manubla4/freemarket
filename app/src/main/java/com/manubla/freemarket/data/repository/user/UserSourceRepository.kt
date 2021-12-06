package com.manubla.freemarket.data.repository.user

import com.manubla.freemarket.data.model.User

interface UserSourceRepository {
    suspend fun fetchUser(id: Long): User?
}