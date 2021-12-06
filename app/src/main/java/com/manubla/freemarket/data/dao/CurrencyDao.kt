package com.manubla.freemarket.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${Currency.TABLE_NAME} WHERE id=:id")
    suspend fun getById(id: String): Currency?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)

}