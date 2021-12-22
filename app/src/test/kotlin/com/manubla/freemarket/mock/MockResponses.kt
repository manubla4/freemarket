package com.manubla.freemarket.mock

import com.manubla.freemarket.data.model.business.*
import com.manubla.freemarket.data.source.network.service.response.PagingData
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

fun getMockCurrencies(): List<Currency> = listOf(
    Currency("1", "$", "", 2),
    Currency("2", "$", "", 2))

fun getMockUser() = User(1, "user",
    Address("Centro","Montevideo"),
    SellerReputation("1","status"))

fun getMockState() = State("1", "Montevideo")

fun getMockProduct() = Product("1", "CocaCola",
    100.0, "new", "",
    Shipping(false), listOf(),
    "",0L, "" )

fun getMockProductQuery() = ProductCurrencyQuery("1", "CocaCola",
    100.0, "new", "", "",
    "$", listOf(), false, 0L)

fun getMockProductPage() = ProductsPageResponse("",
    PagingData(0L, 0, 0, 0),
    listOf(getMockProduct()))