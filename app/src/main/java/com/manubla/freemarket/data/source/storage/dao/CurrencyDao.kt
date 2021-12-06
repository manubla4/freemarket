package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.model.Product

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${Currency.TABLE_NAME} WHERE ${Currency.PARAM_ID}=:id")
    suspend fun getById(id: String): Currency?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)

}