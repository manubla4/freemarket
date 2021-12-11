package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address (

    @ColumnInfo(name = PARAM_CITY)
    @SerializedName(PARAM_CITY)
    private val _city: String?,

    @ColumnInfo(name = PARAM_STATE)
    @SerializedName(PARAM_STATE)
    private val _state: String?

): Parcelable {

    val city: String
        get() = _city.toNotNullable()
    val state: String
        get() = _state.toNotNullable()

    companion object {
        internal const val PARAM_CITY = "city"
        internal const val PARAM_STATE = "state"
    }
}