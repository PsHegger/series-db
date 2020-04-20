package io.github.pshegger.seriesdb.network

import com.squareup.moshi.FromJson
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime

class LocalDateAdapter {

    @FromJson
    fun fromJson(date: String?): LocalDate? = date?.let {
        LocalDate.parse(it)
    }
}

class OffsetDateTimeAdapter {

    @FromJson
    fun fromJson(date: String?): OffsetDateTime? = date?.let {
        OffsetDateTime.parse(it)
    }
}
