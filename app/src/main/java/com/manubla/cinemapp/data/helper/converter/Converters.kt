package com.manubla.cinemapp.data.helper.converter

import androidx.room.TypeConverter
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?) =
        if (value != null) ZonedDateTime.parse(value)
        else null

    @TypeConverter
    fun dateToTimestamp(date: ZonedDateTime?) =
        if (date != null) DateTimeFormatter.ISO_ZONED_DATE_TIME.format(date)
        else null

}