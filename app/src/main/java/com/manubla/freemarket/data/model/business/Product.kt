package com.manubla.freemarket.data.model.business

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.extension.toNotNullable
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
    private val _price: Double?,

    @ColumnInfo(name = PARAM_CONDITION)
    @SerializedName(PARAM_CONDITION)
    private val _condition: String?,

    @ColumnInfo(name = PARAM_THUMBNAIL)
    @SerializedName(PARAM_THUMBNAIL)
    private val _thumbnail: String?,

    @Embedded
    @SerializedName(PARAM_SHIPPING)
    val _shipping: Shipping,

    @ColumnInfo(name = PARAM_PICTURES)
    @SerializedName(PARAM_PICTURES)
    private val _pictures: List<Picture>?,

    @ColumnInfo(name = PARAM_CURRENCY_ID)
    @SerializedName(PARAM_CURRENCY_ID)
    private val _currencyId: String?,

    @ColumnInfo(name = PARAM_SELLER_ID)
    @SerializedName(PARAM_SELLER_ID)
    private val _sellerId: Long?,

    @ColumnInfo(name = PARAM_WARRANTY)
    @SerializedName(PARAM_WARRANTY)
    private val _warranty: String?

): Parcelable, Model() {

    val title: String
        get() = _title.toNotNullable()

    val price: Double
        get() = _price.toNotNullable()

    val condition: String
        get() = _condition.toNotNullable()

    val thumbnail: String
        get() = _thumbnail
            .toNotNullable()
            .replaceFirst(oldValue = HTTP,
                newValue = HTTPS,
                ignoreCase = false)

    val currencyId: String
        get() = _currencyId.toNotNullable()

    val sellerId: Long
        get() = _sellerId.toNotNullable()

    val freeShipping: Boolean
        get() = _shipping.freeShipping

    val pictures: List<Picture>
        get() = _pictures ?: emptyList()

    val warranty: String
        get() = _warranty.toNotNullable()

    override fun requiredParams() = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_TITLE, _title),
        Pair(PARAM_PRICE, _price)
    )

    override fun areContentsTheSame(newItem: Model): Boolean {
        val newProduct = newItem as? Product
        return newProduct?.let {
            this.id == it.id && this.title == it.title
        } ?: false
    }

    override fun assembleEntityAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "product"
        internal const val PARAM_ID = "id"
        internal const val PARAM_TITLE = "title"
        internal const val PARAM_PRICE = "price"
        internal const val PARAM_SELLER_ID = "seller_id"
        internal const val PARAM_CONDITION = "condition"
        internal const val PARAM_THUMBNAIL = "thumbnail"
        internal const val PARAM_SHIPPING = "shipping"
        internal const val PARAM_CURRENCY_ID = "currency_id"
        internal const val PARAM_PICTURES = "pictures"
        internal const val PARAM_WARRANTY = "warranty"
        internal const val CONDITION_TYPE_NEW = "new"
        private const val HTTP = "http://"
        private const val HTTPS = "https://"
    }
}