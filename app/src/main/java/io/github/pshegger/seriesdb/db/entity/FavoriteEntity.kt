package io.github.pshegger.seriesdb.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.pshegger.seriesdb.model.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val name: String,
    val language: String?,
    val status: String,
    val runtime: Int?,
    val premiered: String?,
    @ColumnInfo(name = "official_site") val officialSite: String?,
    @ColumnInfo(name = "average_rating") val averageRating: Float?,
    @ColumnInfo(name = "medium_image") val mediumImage: String?,
    @ColumnInfo(name = "original_image") val originalImage: String?,
    val summary: String?
) {

    companion object {
        fun fromShow(show: Show) = FavoriteEntity(
            show.id,
            show.url,
            show.name,
            show.language,
            show.status.name,
            show.runtime,
            show.premiered?.format(DateTimeFormatter.ISO_DATE),
            show.officialSite,
            show.rating.average,
            show.image?.medium,
            show.image?.original,
            show.summary
        )
    }

    fun toShow(genres: List<Genre>): Show {
        val image = if (mediumImage != null && originalImage != null) {
            ShowImage(mediumImage, originalImage)
        } else {
            null
        }

        return Show(
            id,
            url,
            name,
            language,
            genres,
            Status.valueOf(status),
            runtime,
            premiered?.let { LocalDate.parse(it) },
            officialSite,
            ShowRating(averageRating),
            image,
            summary
        )
    }
}
