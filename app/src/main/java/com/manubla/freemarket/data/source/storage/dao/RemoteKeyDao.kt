package com.manubla.freemarket.data.source.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manubla.freemarket.data.source.storage.entity.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM ${RemoteKey.TABLE_NAME} WHERE ${RemoteKey.PARAM_ID} = :id")
    suspend fun getById(id: String): RemoteKey?

    @Query("DELETE FROM ${RemoteKey.TABLE_NAME}")
    suspend fun deleteAll()

}

