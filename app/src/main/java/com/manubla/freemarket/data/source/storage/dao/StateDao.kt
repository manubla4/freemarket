package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.model.User

@Dao
interface StateDao {

    @Query("SELECT * FROM ${State.TABLE_NAME} WHERE ${State.PARAM_ID}=:id")
    suspend fun getById(id: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: State)

    @Query("DELETE FROM ${State.TABLE_NAME}")
    suspend fun deleteAll()

}