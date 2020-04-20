package io.github.pshegger.seriesdb.utils

fun CharSequence.stripTags() = replace("<[^>]*>".toRegex(), "")
