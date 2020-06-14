package com.manubla.restoya.data.repository.token

import com.manubla.restoya.data.service.TokenService
import com.manubla.restoya.data.service.response.TokenResponse

class TokenDataStoreCloud(private var tokenService: TokenService) {


    suspend fun getToken(): TokenResponse = try {
            tokenService.getToken()
        } catch (error: Exception) {
            TokenResponse("")
        }

}