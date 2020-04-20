package io.github.pshegger.seriesdb.di

import io.github.pshegger.seriesdb.datasource.ShowApiDataSource
import io.github.pshegger.seriesdb.datasource.FavoritesStoreDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { ShowApiDataSource(get()) }
    factory { FavoritesStoreDataSource(get(), get()) }
}
