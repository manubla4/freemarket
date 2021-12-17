package com.manubla.freemarket.data.source.network.service

import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("sites/{siteId}/search")
    suspend fun fetchProductsPage(@Path("siteId") siteId: String,
                                  @Query("q") query: String,
                                  @Query("offset") offset: Int,
                                  @Query("limit") limit: Int): ProductsPageResponse

    @GET("items/{itemId}")
    suspend fun fetchProduct(@Path("itemId") itemId: String): Product

}
