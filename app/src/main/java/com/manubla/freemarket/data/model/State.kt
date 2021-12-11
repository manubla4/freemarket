package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = State.TABLE_NAME)
data class State (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    @SerializedName(PARAM_ID)
    val id: String,

    @ColumnInfo(name = PARAM_NAME)
    @SerializedName(PARAM_NAME)
    private val _name: String?

): Parcelable, Model {

    val name: String
        get() = _name.toNotNullable()

    @Ignore
    @IgnoredOnParcel
    override val requiredParams = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_NAME, _name)
    )

    override fun areContentsTheSame(newItem: Model): Boolean {
        val newState = newItem as? State
        return newState?.let {
            this.id == it.id && this.name == it.name
        } ?: false
    }

    override fun assembleTableAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "state"
        internal const val PARAM_ID = "id"
        internal const val PARAM_NAME = "name"
    }
}