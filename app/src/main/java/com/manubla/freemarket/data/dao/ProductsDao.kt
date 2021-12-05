package com.manubla.freemarket.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.Product

@Dao
interface ProductsDao {

    @Query("SELECT * FROM ${Product.TABLE_NAME}")
    suspend fun getAll(): List<Product>

    @Query("SELECT * FROM ${Product.TABLE_NAME} LIMIT :offset, :rows")
    suspend fun getAllWithOffset(offset: Int, rows: Int): List<Product>

    @Query("SELECT COUNT(1) FROM ${Product.TABLE_NAME}")
    suspend fun getRowsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE FROM ${Product.TABLE_NAME}")
    suspend fun deleteAll()

}