package com.manubla.cinemapp.data.service.response

import android.os.Parcelable
import com.manubla.cinemapp.data.model.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreResponse (
    val genres: List<Genre>
): Parcelable