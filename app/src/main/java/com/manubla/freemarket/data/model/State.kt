package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.utils.toNotNullable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = State.TABLE_NAME)
data class State (

    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    @SerializedName(PARAM_ID)
    private val _id: String?,

    @ColumnInfo(name = PARAM_NAME)
    @SerializedName(PARAM_NAME)
    private val _name: String?

): Parcelable, Model() {

    val id: String
        get() = _id.toNotNullable()

    val name: String
        get() = _name.toNotNullable()

    @IgnoredOnParcel
    override val requiredParams = mapOf(
        Pair(PARAM_ID, _id),
        Pair(PARAM_NAME, _name)
    )

    override fun assembleTableAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "state"
        internal const val PARAM_ID = "id"
        internal const val PARAM_NAME = "name"
    }
}