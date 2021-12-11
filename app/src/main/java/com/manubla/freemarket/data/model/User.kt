package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = User.TABLE_NAME,
    foreignKeys = [ForeignKey(entity = State::class,
        parentColumns = arrayOf(State.PARAM_ID),
        childColumns = arrayOf(Address.PARAM_STATE)
    )]
)
data class User (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = PARAM_ID)
    @SerializedName(PARAM_ID)
    val id: Long,

    @ColumnInfo(name = PARAM_NICKNAME)
    @SerializedName(PARAM_NICKNAME)
    private val _nickname: String?,

    @Embedded
    @SerializedName(PARAM_ADDRESS)
    val _address: Address,

    @Embedded
    @SerializedName(PARAM_SELLER_REPUTATION)
    val _sellerReputation: SellerReputation

): Parcelable, Model() {

    val nickname: String
        get() = _nickname.toNotNullable()

    val city: String
        get() = _address.city

    val stateId: String
        get() = _address.state

    val levelId: String
        get() = _sellerReputation.levelId

    val powerSellerStatus: String
        get() = _sellerReputation.powerSellerStatus

    @Ignore
    @IgnoredOnParcel
    override val requiredParams = mapOf(
        Pair(PARAM_ID, id),
        Pair(PARAM_NICKNAME, _nickname)
    )

    override fun assembleTableAndParam(param: String): String
        = "$TABLE_NAME - $param"

    companion object {
        internal const val TABLE_NAME = "user"
        internal const val PARAM_ID = "id"
        internal const val PARAM_NICKNAME = "nickname"
        internal const val PARAM_ADDRESS = "address"
        internal const val PARAM_SELLER_REPUTATION = "seller_reputation"
    }
}