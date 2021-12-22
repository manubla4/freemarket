package com.manubla.freemarket.data.source.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

@Dao
interface ProductDao {

    @Query("SELECT t1.*, t2.symbol as currency FROM ${Product.TABLE_NAME} t1 LEFT JOIN ${Currency.TABLE_NAME} t2 ON t1.${Product.PARAM_CURRENCY_ID}=t2.${Currency.PARAM_ID} WHERE t1.${Product.PARAM_ID}=:id")
    suspend fun getById(id: String): ProductCurrencyQuery?

    @Query("SELECT t1.*, t2.symbol as currency FROM ${Product.TABLE_NAME} t1 LEFT JOIN ${Currency.TABLE_NAME} t2 ON t1.${Product.PARAM_CURRENCY_ID}=t2.${Currency.PARAM_ID}")
    fun getPagingSource(): PagingSource<Int, ProductCurrencyQuery>

    @Query("SELECT t1.*, t2.symbol as currency FROM ${Product.TABLE_NAME} t1 LEFT JOIN ${Currency.TABLE_NAME} t2 ON t1.${Product.PARAM_CURRENCY_ID}=t2.${Currency.PARAM_ID}")
    suspend fun getAll(): List<ProductCurrencyQuery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE FROM ${Product.TABLE_NAME}")
    suspend fun deleteAll()

}