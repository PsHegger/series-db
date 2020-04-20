package io.github.pshegger.seriesdb.model

import org.threeten.bp.LocalDate

data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val language: String?,
    val genres: List<Genre>,
    val status: Status,
    val runtime: Int?,
    val premiered: LocalDate?,
    val officialSite: String?,
    val rating: ShowRating,
    val image: ShowImage?,
    val summary: String?
)
