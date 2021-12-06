package com.manubla.freemarket.data.service

import com.manubla.freemarket.data.model.Currency
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    suspend fun fetchCurrencies(): Currency

}
