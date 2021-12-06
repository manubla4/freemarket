package com.manubla.freemarket.data.repository.currency

import com.manubla.freemarket.data.model.Currency

interface CurrencySourceRepository {
    suspend fun fetchCurrency(id: String): Currency?
}