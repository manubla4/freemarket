package com.manubla.freemarket.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

const val dateFormat = "yyyy-MM-dd"

class ZonedDateTimeAdapter : TypeAdapter<ZonedDateTime>() {

    override fun write(out: JsonWriter, date: ZonedDateTime) {
        out.value(DateTimeFormatter.ofPattern(dateFormat).format(date))
    }

    override fun read(`in`: JsonReader): ZonedDateTime =
        LocalDate.parse(`in`.nextString(), DateTimeFormatter.ofPattern(dateFormat))
            .atStartOfDay(ZoneId.systemDefault())

}
