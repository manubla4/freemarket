package com.manubla.freemarket.data.repository.token

import android.content.SharedPreferences
import com.manubla.freemarket.data.service.response.TokenResponse

class TokenDataStoreStorage(private var sharedPreferences: SharedPreferences) {

    private val keyToken = "keyToken"

    fun getToken(): TokenResponse = TokenResponse(sharedPreferences.getString(keyToken, "") ?: "")

    fun storeToken(token: String) {
        try {
            sharedPreferences.edit().putString(keyToken, token).commit()
        }
        catch (error: Exception) {
            //do nothing
        }
    }

}