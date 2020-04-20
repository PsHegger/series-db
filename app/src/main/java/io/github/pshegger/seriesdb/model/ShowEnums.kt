package io.github.pshegger.seriesdb.model

import com.squareup.moshi.FromJson

enum class Genre(private val jsonName: String? = null) {
    Action, Adventure, Anime, Comedy,
    Crime, Drama, Espionage, Family,
    Fantasy, History, Horror, Legal,
    Medical, Music, Mystery, Romance,
    SciFi("Science-Fiction"), Sports, Supernatural,
    Thriller, War, Western, Unknown;

    class GenreAdapter {

        @FromJson
        fun fromJson(genre: String): Genre =
            values().find { (it.jsonName ?: it.name) == genre } ?: Unknown
    }
}

enum class Status(private val jsonName: String? = null) {
    InDevelopment("In Development"),
    Ended,
    Running,
    TBD("To Be Determined"),
    Unknown;

    class StatusAdapter {

        @FromJson
        fun fromJson(status: String): Status =
            values().find { (it.jsonName ?: it.name) == status } ?: Unknown
    }
}
