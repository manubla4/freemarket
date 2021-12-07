package com.manubla.freemarket.data.repository.currency

interface CurrencySourceRepository {
    suspend fun fetchCurrencies()
}