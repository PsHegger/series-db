package io.github.pshegger.seriesdb.utils

import android.content.Context

fun <T : Enum<T>> T.localizedName(context: Context, prefix: String) = context.getString(
    context.resources.getIdentifier("${prefix}_${name.toLowerCase()}", "string", context.packageName)
)
