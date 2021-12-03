package com.manubla.freemarket.data.service

import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.service.response.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenService {

    @GET("tokens")
    suspend fun getToken(@Query("clientId") clientId: String = BuildConfig.CLIENT_ID,
                         @Query("clientSecret") clientSecret: String = BuildConfig.CLIENT_SECRET): TokenResponse

}
