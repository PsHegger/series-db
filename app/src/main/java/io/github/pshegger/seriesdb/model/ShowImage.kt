package io.github.pshegger.seriesdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShowImage(
    val medium: String,
    val original: String
): Parcelable
