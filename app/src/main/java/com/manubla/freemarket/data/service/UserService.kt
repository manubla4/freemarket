package com.manubla.freemarket.data.service

import com.manubla.freemarket.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userId}")
    suspend fun fetchUser(@Path("userId") itemId: Long): User

}
