package com.manubla.restoya.data.service.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenResponse (
    val access_token: String
): Parcelable