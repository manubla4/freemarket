package com.manubla.freemarket.data.repository.restaurants

import com.manubla.freemarket.data.dao.ProductsDao
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse

class RestaurantsDataStoreImplDatabase(private val productsDao: ProductsDao) : RestaurantsDataStore {

    private val defaultPageSize = 20

    override suspend fun getRestaurantsPage(latitude: Double?,
                                            longitude: Double?,
                                            offset: Int): RestaurantsPageResponse {
        val results = productsDao.getAllWithOffset(offset, defaultPageSize)
        val total = productsDao.getRowsCount()
        return RestaurantsPageResponse(total = total,
                                       max = defaultPageSize,
                                       sort = "",
                                       count = results.size,
                                       data = results,
                                       offset = offset)
    }


    suspend fun getAllRestaurants(): List<Product> =
        productsDao.getAll()

    
    suspend fun storeRestaurants(products: List<Product>) {
        productsDao.insertAll(products)
    }

    suspend fun deleteRestaurants() {
        productsDao.deleteAll()
    }

}