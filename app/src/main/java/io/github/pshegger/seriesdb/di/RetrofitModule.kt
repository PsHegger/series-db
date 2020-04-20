package io.github.pshegger.seriesdb.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.github.pshegger.seriesdb.BuildConfig
import io.github.pshegger.seriesdb.model.Genre
import io.github.pshegger.seriesdb.model.Status
import io.github.pshegger.seriesdb.network.LocalDateAdapter
import io.github.pshegger.seriesdb.network.OffsetDateTimeAdapter
import io.github.pshegger.seriesdb.network.RetrofitConfig
import io.github.pshegger.seriesdb.network.RetrofitManager
import org.koin.dsl.module

val retrofitModule = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Genre.GenreAdapter())
            .add(Status.StatusAdapter())
            .add(LocalDateAdapter())
            .add(OffsetDateTimeAdapter())
            .build()
    }

    single {
        RetrofitConfig(
            BuildConfig.BASE_URL,
            get(),
            get()
        )
    }

    single { RetrofitManager(get()).create() }
}
