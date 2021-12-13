package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.model.business.Product

@Dao
interface ProductDao {

    @Query("SELECT t1.*, t2.symbol as currency FROM ${Product.TABLE_NAME} t1 LEFT JOIN ${Currency.TABLE_NAME} t2 ON t1.${Product.PARAM_CURRENCY_ID}=t2.${Currency.PARAM_ID} WHERE t1.${Product.PARAM_ID}=:id")
    fun getById(id: String): Product?

    @Query("SELECT t1.*, t2.symbol FROM ${Product.TABLE_NAME} t1 LEFT JOIN ${Currency.TABLE_NAME} t2 ON t1.${Product.PARAM_CURRENCY_ID}=t2.${Currency.PARAM_ID} WHERE t1.${Product.PARAM_TITLE} LIKE '%' || :query || '%'")
    fun getAll(query: String): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Query("DELETE FROM ${Product.TABLE_NAME}")
    fun deleteAll()

}