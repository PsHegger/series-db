package io.github.pshegger.seriesdb.model

import com.squareup.moshi.Json
import org.threeten.bp.LocalDate

data class ShowWithEmbed<T>(
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
    val summary: String?,
    @Json(name = "_embedded") val embedded: T
) {

    val show get() = Show(
        id,
        url,
        name,
        language,
        genres,
        status,
        runtime,
        premiered,
        officialSite,
        rating,
        image,
        summary
    )
}
