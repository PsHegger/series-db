package io.github.pshegger.seriesdb.utils

import com.jakewharton.rxrelay2.Relay
import io.reactivex.Maybe

fun Relay<Unit>.trigger() = accept(Unit)

fun <T : Any> T?.toMaybe(): Maybe<T> = this?.let { Maybe.just(it) } ?: Maybe.empty()
