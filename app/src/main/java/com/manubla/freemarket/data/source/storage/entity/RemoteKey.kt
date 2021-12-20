package com.manubla.freemarket.data.source.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteKey.TABLE_NAME)
data class RemoteKey(
    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    val id: String,

    @ColumnInfo(name = PARAM_PREV_KEY)
    val prevKey: Int?,

    @ColumnInfo(name = PARAM_NEXT_KEY)
    val nextKey: Int?
) {
    companion object {
        internal const val TABLE_NAME = "remote_key"
        internal const val PARAM_ID = "id"
        internal const val PARAM_PREV_KEY = "prev_key"
        internal const val PARAM_NEXT_KEY = "next_key"
    }
}