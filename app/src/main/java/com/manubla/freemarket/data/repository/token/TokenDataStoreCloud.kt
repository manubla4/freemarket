package com.manubla.freemarket.data.repository.token

import com.manubla.freemarket.data.helper.networking.NetworkingManager
import com.manubla.freemarket.data.service.TokenService
import com.manubla.freemarket.data.service.response.TokenResponse

class TokenDataStoreCloud(private val tokenService: TokenService,
                          private val networkingManager: NetworkingManager) {


    suspend fun getToken(): TokenResponse = try {
            if (networkingManager.isNetworkOnline())
                tokenService.getToken()
            else
                TokenResponse("")
        } catch (error: Exception) {
            TokenResponse("")
        }
}