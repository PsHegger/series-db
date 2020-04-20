package io.github.pshegger.seriesdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.OffsetDateTime

@Parcelize
data class Episode(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val airstamp: OffsetDateTime?,
    val runtime: Int?,
    val image: ShowImage?,
    val summary: String?
) : Parcelable
