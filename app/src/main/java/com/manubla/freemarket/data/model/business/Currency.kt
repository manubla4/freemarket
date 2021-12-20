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
@Entity(tableName = Currency.TABLE_NAME)
data class Currency (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    @SerializedName(PARAM_ID)
    val id: String,

    @ColumnInfo(name = PARAM_SYMBOL)
    @SerializedName(PARAM_SYMBOL)
    private val _symbol: String?,

    @ColumnInfo(name = PARAM_DESCRIPTION)
    @SerializedName(PARAM_DESCRIPTION)
    private val _description: String?,

    @ColumnInfo(name = PARAM_DECIMAL_PLACES)
    @SerializedName(PARAM_DECIMAL_PLACES)
    private val _decimalPlaces: Int?

): Parcelable, Model() {

    val symbol: String
        get() = _symbol.toNotNullable()

    val description: String
        get() = _description.toNotNullable()

    val decimalPlaces: Int
        get() = _decimalPlaces.toNotNullable()

    override fun requiredParams() = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_SYMBOL, _symbol)
    )

    override fun areContentsTheSame(newItem: Model): Boolean {
        val newCurrency = newItem as? Currency
        return newCurrency?.let {
            this.id == it.id && this.symbol == it.symbol
        } ?: false
    }

    override fun assembleEntityAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "currency"
        internal const val PARAM_ID = "id"
        internal const val PARAM_SYMBOL = "symbol"
        internal const val PARAM_DESCRIPTION = "description"
        internal const val PARAM_DECIMAL_PLACES = "decimal_places"
    }
}