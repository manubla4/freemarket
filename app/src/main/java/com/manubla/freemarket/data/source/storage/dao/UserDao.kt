package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.model.business.Address
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.model.business.User

@Dao
interface UserDao {

    @Query("SELECT t1.*, t2.name as state FROM ${User.TABLE_NAME} t1 LEFT JOIN ${State.TABLE_NAME} t2 ON t1.${Address.PARAM_STATE}=t2.${State.PARAM_ID} WHERE t1.${User.PARAM_ID}=:id")
    fun getById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM ${User.TABLE_NAME}")
    fun deleteAll()

}