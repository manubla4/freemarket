package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.business.Currency
import com.manubla.freemarket.data.model.business.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${Product.TABLE_NAME} LEFT JOIN ${Currency.TABLE_NAME} ON ${Product.TABLE_NAME}.${Product.PARAM_CURRENCY_ID}=${Currency.TABLE_NAME}.${Currency.PARAM_ID} WHERE ${Product.TABLE_NAME}.${Product.PARAM_ID}=:id")
    suspend fun getById(id: String): Product?

    @Query("SELECT * FROM ${Product.TABLE_NAME} LEFT JOIN ${Currency.TABLE_NAME} ON ${Product.TABLE_NAME}.${Product.PARAM_CURRENCY_ID}=${Currency.TABLE_NAME}.${Currency.PARAM_ID} WHERE ${Product.TABLE_NAME}.${Product.PARAM_TITLE} LIKE '%' || :query || '%'")
    suspend fun getAll(query: String): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE FROM ${Product.TABLE_NAME}")
    suspend fun deleteAll()

}