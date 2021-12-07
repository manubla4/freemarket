package com.manubla.freemarket.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.repository.currency.CurrencySourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    private val currencySourceRepository: CurrencySourceRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _done = MutableLiveData<Boolean>()
    val done: LiveData<Boolean>
        get() = _done

    fun fetchInitialData() {
        launch(Dispatchers.IO) {
            currencySourceRepository.fetchCurrencies()
            _done.postValue(true)
        }
    }
}