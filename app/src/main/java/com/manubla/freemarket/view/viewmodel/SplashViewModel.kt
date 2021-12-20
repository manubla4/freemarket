package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.repository.currency.CurrencySourceRepository
import com.manubla.freemarket.view.event.SplashState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    private val currencySourceRepository: CurrencySourceRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _state = MutableLiveData<SplashState>()
    val state: LiveData<SplashState>
        get() = _state

    fun fetchInitialData() {
        launch(Dispatchers.IO) {
            currencySourceRepository.fetchCurrencies()
            _state.postValue(SplashState.Done)
        }
    }

    fun onNavigationComplete() {
        _state.postValue(SplashState.Navigated)
    }
}