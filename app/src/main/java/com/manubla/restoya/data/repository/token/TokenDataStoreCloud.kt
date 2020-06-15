package com.manubla.restoya.data.repository.token

import com.manubla.restoya.data.helper.networking.NetworkingManager
import com.manubla.restoya.data.service.TokenService
import com.manubla.restoya.data.service.response.TokenResponse

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