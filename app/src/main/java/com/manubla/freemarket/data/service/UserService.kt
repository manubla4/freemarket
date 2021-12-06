package com.manubla.freemarket.data.service

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.model.User
import com.manubla.freemarket.data.service.response.ProductsPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users/{userId}")
    suspend fun fetchUser(@Path("userId") itemId: Long): User

}
