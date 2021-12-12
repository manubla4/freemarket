package com.manubla.freemarket.data.model.business

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture (

@ColumnInfo(name = PARAM_SECURE_URL)
@SerializedName(PARAM_SECURE_URL)
private val _url: String?,

): Parcelable {

    val url: String
    get() = _url.toNotNullable()

    companion object {
        internal const val PARAM_SECURE_URL = "secure_url"
    }
}