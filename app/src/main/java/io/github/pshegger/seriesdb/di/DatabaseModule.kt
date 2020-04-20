package io.github.pshegger.seriesdb.di

import androidx.room.Room
import io.github.pshegger.seriesdb.db.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "series-db"
        ).build()
    }

    single { get<AppDatabase>().favoritesDao() }
    single { get<AppDatabase>().genreDao() }
}
