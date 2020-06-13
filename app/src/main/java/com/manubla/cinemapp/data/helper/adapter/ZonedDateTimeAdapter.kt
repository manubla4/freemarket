package com.manubla.cinemapp.data.helper.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.manubla.cinemapp.data.helper.dateFormat
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeAdapter : TypeAdapter<ZonedDateTime>() {

    override fun write(out: JsonWriter, date: ZonedDateTime) {
        out.value(DateTimeFormatter.ofPattern(dateFormat).format(date))
    }

    override fun read(`in`: JsonReader): ZonedDateTime =
        LocalDate.parse(`in`.nextString(), DateTimeFormatter.ofPattern(dateFormat))
            .atStartOfDay(ZoneId.systemDefault())

}
