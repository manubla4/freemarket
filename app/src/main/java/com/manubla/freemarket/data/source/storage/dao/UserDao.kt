package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE ${User.PARAM_ID}=:id")
    suspend fun getById(id: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM ${User.TABLE_NAME}")
    suspend fun deleteAll()

}