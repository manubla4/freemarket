package com.manubla.cinemapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = MovieGenre.TABLE_NAME,
    primaryKeys = ["movie_id", "genre_id"],
    foreignKeys = [ForeignKey(entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movie_id")
    ), ForeignKey(entity = Genre::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("genre_id")
    )]
)
data class MovieGenre (
    @ColumnInfo(name = "movie_id") var movieId: Int,
    @ColumnInfo(name = "genre_id") var genreId: Int
): Parcelable {
    companion object {
        const val TABLE_NAME = "moviegenres"
    }
}