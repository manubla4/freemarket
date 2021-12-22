package com.manubla.freemarket.mock

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manubla.freemarket.data.model.business.*
import com.manubla.freemarket.data.source.network.service.response.PagingData
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse
import com.manubla.freemarket.data.source.storage.entity.RemoteKey
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

fun getMockRemoteKey() = RemoteKey("1", null,2)

fun getMockRemoteKeys() = listOf(RemoteKey("1", null,2),
    RemoteKey("2", null,2),
    RemoteKey("3", null,2))

fun getMockCurrencies(): List<Currency> = listOf(
    Currency("1", "$", "", 2),
    Currency("2", "$", "", 2))

fun getMockUser() = User(1, "user",
    Address("Centro","1"),
    SellerReputation("1","status"))

fun getMockState() = State("1", "Montevideo")

fun getMockProduct() = Product("1", "CocaCola",
    100.0, "new", "",
    Shipping(false), listOf(),
    "1",0L, "" )

fun getMockProducts() = listOf(
    Product("1", "CocaCola",
    100.0, "new", "",
    Shipping(false), listOf(),
    "1",0L, "" ),
    Product("2", "CocaCola",
        100.0, "new", "",
        Shipping(false), listOf(),
        "2",0L, "" ),
    Product("3", "CocaCola",
        100.0, "new", "",
        Shipping(false), listOf(),
        "1",0L, "" )
)

fun getMockProductQuery() = ProductCurrencyQuery("123", "Samsung",
    100.0, "new", "", "",
    "$", listOf(), false, 0L)

fun getMockProductPage() = ProductsPageResponse("",
    PagingData(0L, 0, 0, 0),
    listOf(getMockProduct()))

fun getMockPagingSource(): PagingSource<Int, ProductCurrencyQuery>
    = MockPagingSource()


private class MockPagingSource: PagingSource<Int, ProductCurrencyQuery>() {
    override fun getRefreshKey(state: PagingState<Int, ProductCurrencyQuery>): Int? {
        TODO("Not implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductCurrencyQuery> {
        TODO("Not implemented")
    }

}