package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.business.*

@Dao
interface UserDao {

    @Query("SELECT * FROM ${User.TABLE_NAME} LEFT JOIN ${State.TABLE_NAME} ON ${User.TABLE_NAME}.${Address.PARAM_STATE}=${State.TABLE_NAME}.${State.PARAM_ID} WHERE ${User.PARAM_ID}=:id")
    suspend fun getById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM ${User.TABLE_NAME}")
    suspend fun deleteAll()

}