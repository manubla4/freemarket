package com.manubla.freemarket.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM ${Product.TABLE_NAME} WHERE id=:id")
    suspend fun getById(id: String): Product?

    @Query("SELECT * FROM ${Product.TABLE_NAME}")
    suspend fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE FROM ${Product.TABLE_NAME}")
    suspend fun deleteAll()

}