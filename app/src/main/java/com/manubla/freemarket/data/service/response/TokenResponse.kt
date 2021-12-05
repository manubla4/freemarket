package com.manubla.freemarket.data.service.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenResponse (
    val access_token: String
): Parcelable