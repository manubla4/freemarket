package com.manubla.freemarket.data.service

import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.response.ProductsPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyService {

    @GET("currencies")
    suspend fun fetchCurrencies(): Currency

}
