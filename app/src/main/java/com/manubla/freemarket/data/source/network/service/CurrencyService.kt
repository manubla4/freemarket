package com.manubla.freemarket.data.source.network.service

import com.manubla.freemarket.data.model.business.Currency
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    suspend fun fetchCurrencies(): List<Currency>

}
