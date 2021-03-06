package com.manubla.freemarket.data.source.network.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeAdapter : TypeAdapter<ZonedDateTime>() {

    override fun write(out: JsonWriter, date: ZonedDateTime) {
        out.value(DateTimeFormatter.ofPattern(DATE_FORMAT).format(date))
    }

    override fun read(`in`: JsonReader): ZonedDateTime =
        LocalDate.parse(`in`.nextString(), DateTimeFormatter.ofPattern(DATE_FORMAT))
            .atStartOfDay(ZoneId.systemDefault())

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-ddThh:mm:ss.sTZD"
    }
}
