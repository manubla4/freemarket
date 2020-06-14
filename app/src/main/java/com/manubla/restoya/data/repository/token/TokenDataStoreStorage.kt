package com.manubla.restoya.data.repository.token

import android.content.SharedPreferences
import com.manubla.restoya.data.service.response.TokenResponse

class TokenDataStoreStorage(private var sharedPreferences: SharedPreferences) {

    private val keyToken = "keyToken"

    fun getToken(): TokenResponse {
        return TokenResponse(sharedPreferences.getString(keyToken, "") ?: "")
    }

    fun storeToken(token: String) {
        sharedPreferences.edit().putString(keyToken, token).commit()
    }

}