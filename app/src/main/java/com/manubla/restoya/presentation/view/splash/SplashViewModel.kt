package com.manubla.restoya.presentation.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.repository.token.TokenDataStoreCloud
import com.manubla.restoya.data.repository.token.TokenDataStoreStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(private val tokenDataStoreCloud: TokenDataStoreCloud,
                      private val tokenDataStoreStorage: TokenDataStoreStorage) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val token: LiveData<String>
        get() = localToken

    private val localToken = MutableLiveData<String>()

    fun fetchToken() {
        launch(Dispatchers.IO) {
            val tokenResponse = tokenDataStoreCloud.getToken()
            tokenDataStoreStorage.storeToken(tokenResponse.access_token)
            localToken.postValue(tokenResponse.access_token)
        }
    }


}