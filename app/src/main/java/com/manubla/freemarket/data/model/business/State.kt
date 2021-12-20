package com.manubla.freemarket.data.model.business

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.extension.toNotNullable
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

): Parcelable, Model() {

    val name: String
        get() = _name.toNotNullable()

    override fun requiredParams() = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_NAME, _name)
    )

    override fun areContentsTheSame(newItem: Model): Boolean {
        val newState = newItem as? State
        return newState?.let {
            this.id == it.id && this.name == it.name
        } ?: false
    }

    override fun assembleEntityAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "state"
        internal const val PARAM_ID = "id"
        internal const val PARAM_NAME = "name"
    }
}