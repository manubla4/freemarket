package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.utils.toNotNullable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Product.TABLE_NAME,
    foreignKeys = [ForeignKey(entity = Currency::class,
        parentColumns = arrayOf(Currency.PARAM_ID),
        childColumns = arrayOf(Product.PARAM_CURRENCY_ID)
    )]
)
data class Product (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    @SerializedName(PARAM_ID)
    val id: String,

    @ColumnInfo(name = PARAM_TITLE)
    @SerializedName(PARAM_TITLE)
    private val _title: String?,

    @ColumnInfo(name = PARAM_PRICE)
    @SerializedName(PARAM_PRICE)
    private val _price: Long?,

    @ColumnInfo(name = PARAM_CONDITION)
    @SerializedName(PARAM_CONDITION)
    private val _condition: String?,

    @ColumnInfo(name = PARAM_THUMBNAIL)
    @SerializedName(PARAM_THUMBNAIL)
    private val _thumbnail: String?,

    @ColumnInfo(name = PARAM_CURRENCY_ID)
    @SerializedName(PARAM_CURRENCY_ID)
    private val _currencyId: String?,

    @Expose(serialize = false, deserialize = false)
    private val _currency: String?

): Parcelable, Model() {

    val title: String
        get() = _title.toNotNullable()

    val price: Long
        get() = _price.toNotNullable()

    val condition: String
        get() = _condition.toNotNullable()

    val thumbnail: String
        get() = _thumbnail.toNotNullable()

    val currencyId: String
        get() = _currencyId.toNotNullable()

    val currency: String
        get() = _currency ?: DEFAULT_CURRENCY

    @Ignore
    @IgnoredOnParcel
    override val requiredParams = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_TITLE, _title),
        Pair(PARAM_PRICE, _price)
    )

    override fun assembleTableAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "product"
        internal const val PARAM_ID = "id"
        internal const val PARAM_TITLE = "title"
        internal const val PARAM_PRICE = "price"
        internal const val PARAM_CONDITION = "condition"
        internal const val PARAM_THUMBNAIL = "thumbnail"
        internal const val PARAM_CURRENCY_ID = "currency_id"
        internal const val DEFAULT_CURRENCY = "$"
    }
}